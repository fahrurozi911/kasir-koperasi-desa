package com.kasirkoperasi.view;

import com.kasirkoperasi.dao.BarangDAO;
import com.kasirkoperasi.dao.TransaksiDAO;
import com.kasirkoperasi.model.Barang;
import com.kasirkoperasi.model.DetailTransaksi;
import com.kasirkoperasi.model.Transaksi;
import com.kasirkoperasi.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransaksiPanel extends JPanel {
    private User user;
    private DefaultTableModel cartModel;
    private JTable cartTable;
    private JLabel lblTotal;
    private JTextField txtBayar;
    private JLabel lblKembalian;
    private JComboBox<String> cmbBarang;
    private JTextField txtJumlah;
    private BarangDAO barangDAO = new BarangDAO();
    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private List<Barang> barangList;

    public TransaksiPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel kiri: pemilihan barang
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(350, getHeight()));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tambah Barang"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        barangList = barangDAO.getAll();
        cmbBarang = new JComboBox<>();
        for (Barang b : barangList) {
            cmbBarang.addItem(b.getKodeBarang() + " - " + b.getNamaBarang() + " (Stok: " + b.getStok() + ")");
        }

        txtJumlah = new JTextField(5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Barang:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        inputPanel.add(cmbBarang, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Jumlah:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtJumlah, gbc);
        gbc.gridx = 2;
        JButton btnAdd = new JButton("Tambah");
        btnAdd.addActionListener(e -> addToCart());
        inputPanel.add(btnAdd, gbc);

        leftPanel.add(inputPanel, BorderLayout.NORTH);

        // Tabel keranjang
        String[] col = {"Kode", "Nama", "Harga", "Jumlah", "Subtotal"};
        cartModel = new DefaultTableModel(col, 0);
        cartTable = new JTable(cartModel);
        cartTable.setRowHeight(25);
        leftPanel.add(new JScrollPane(cartTable), BorderLayout.CENTER);

        // Panel kanan: pembayaran
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Pembayaran"));
        JPanel payForm = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblTotal = new JLabel("Rp 0");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0;
        payForm.add(new JLabel("Total:"), gbc);
        gbc.gridx = 1;
        payForm.add(lblTotal, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        payForm.add(new JLabel("Bayar:"), gbc);
        gbc.gridx = 1;
        txtBayar = new JTextField(15);
        txtBayar.addCaretListener(e -> hitungKembalian());
        payForm.add(txtBayar, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        payForm.add(new JLabel("Kembalian:"), gbc);
        gbc.gridx = 1;
        lblKembalian = new JLabel("Rp 0");
        lblKembalian.setFont(new Font("SansSerif", Font.BOLD, 18));
        payForm.add(lblKembalian, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnSimpan = new JButton("Simpan Transaksi");
        btnSimpan.setBackground(new Color(0, 150, 136));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.addActionListener(e -> simpanTransaksi());
        payForm.add(btnSimpan, gbc);

        rightPanel.add(payForm, BorderLayout.NORTH);

        // Layout utama
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(550);
        add(splitPane, BorderLayout.CENTER);
    }

    private void addToCart() {
        int idx = cmbBarang.getSelectedIndex();
        if (idx < 0 || barangList.isEmpty()) return;
        Barang b = barangList.get(idx);
        int jumlah;
        try {
            jumlah = Integer.parseInt(txtJumlah.getText());
            if (jumlah <= 0 || jumlah > b.getStok()) {
                JOptionPane.showMessageDialog(this, "Jumlah tidak valid atau stok tidak cukup!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah angka!");
            return;
        }
        double subtotal = b.getHargaJual() * jumlah;
        cartModel.addRow(new Object[]{b.getKodeBarang(), b.getNamaBarang(), b.getHargaJual(), jumlah, subtotal});
        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            total += (double) cartModel.getValueAt(i, 4);
        }
        lblTotal.setText("Rp " + String.format("%,.0f", total));
        hitungKembalian();
    }

    private void hitungKembalian() {
        double total = getTotal();
        try {
            double bayar = Double.parseDouble(txtBayar.getText());
            double kembali = bayar - total;
            lblKembalian.setText("Rp " + String.format("%,.0f", kembali));
        } catch (NumberFormatException e) {
            lblKembalian.setText("Rp 0");
        }
    }

    private double getTotal() {
        double total = 0;
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            total += (double) cartModel.getValueAt(i, 4);
        }
        return total;
    }

    private void simpanTransaksi() {
        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Keranjang kosong!");
            return;
        }
        double total = getTotal();
        double bayar;
        try {
            bayar = Double.parseDouble(txtBayar.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah bayar yang valid!");
            return;
        }
        if (bayar < total) {
            JOptionPane.showMessageDialog(this, "Uang bayar kurang!");
            return;
        }

        // Buat objek transaksi
        Transaksi t = new Transaksi();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        t.setNoFaktur("INV-" + sdf.format(new Date()));
        t.setTanggal(new Timestamp(System.currentTimeMillis()));
        t.setTotal(total);
        t.setBayar(bayar);
        t.setKembalian(bayar - total);
        t.setIdUser(user.getId());

        List<DetailTransaksi> details = new ArrayList<>();
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            String kode = (String) cartModel.getValueAt(i, 0);
            Barang b = barangDAO.getByKode(kode);
            DetailTransaksi d = new DetailTransaksi();
            d.setIdBarang(b.getIdBarang());
            d.setJumlah((int) cartModel.getValueAt(i, 3));
            d.setSubtotal((double) cartModel.getValueAt(i, 4));
            details.add(d);
        }

        try {
            transaksiDAO.saveTransaction(t, details);
            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!");
            cartModel.setRowCount(0);
            txtBayar.setText("");
            lblTotal.setText("Rp 0");
            lblKembalian.setText("Rp 0");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        }
    }
}