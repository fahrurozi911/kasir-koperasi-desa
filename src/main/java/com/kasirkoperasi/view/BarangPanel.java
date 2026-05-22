package com.kasirkoperasi.view;

import com.kasirkoperasi.dao.BarangDAO;
import com.kasirkoperasi.model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BarangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private BarangDAO barangDAO = new BarangDAO();
    private JTextField txtSearch;

    public BarangPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel atas: judul dan search
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Data Barang");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topPanel.add(title, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        txtSearch = new JTextField(15);
        JButton btnSearch = new JButton("Cari");
        btnSearch.addActionListener(e -> search());
        searchPanel.add(new JLabel("Cari:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        topPanel.add(searchPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Tabel
        String[] columns = {"ID", "Kode", "Nama", "Kategori", "Harga Beli", "Harga Jual", "Stok", "Satuan", "Tgl Masuk"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel bawah: tombol CRUD
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Tambah");
        JButton btnEdit = new JButton("Ubah");
        JButton btnDelete = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event
        btnAdd.addActionListener(e -> openForm(null));
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) tableModel.getValueAt(row, 0);
                Barang b = barangDAO.getByKode((String) tableModel.getValueAt(row, 1));
                openForm(b);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih barang yang akan diubah!");
            }
        });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Hapus barang ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    barangDAO.delete(id);
                    refreshTable();
                }
            }
        });
        btnRefresh.addActionListener(e -> refreshTable());

        refreshTable();
    }

    private void search() {
        String keyword = txtSearch.getText().trim();
        List<Barang> list;
        if (keyword.isEmpty()) {
            list = barangDAO.getAll();
        } else {
            list = barangDAO.search(keyword);
        }
        tableModel.setRowCount(0);
        for (Barang b : list) {
            tableModel.addRow(new Object[]{b.getIdBarang(), b.getKodeBarang(), b.getNamaBarang(), b.getKategori(),
                    b.getHargaBeli(), b.getHargaJual(), b.getStok(), b.getSatuan(), b.getTanggalMasuk()});
        }
    }

    void refreshTable() {
        List<Barang> list = barangDAO.getAll();
        tableModel.setRowCount(0);
        for (Barang b : list) {
            tableModel.addRow(new Object[]{b.getIdBarang(), b.getKodeBarang(), b.getNamaBarang(), b.getKategori(),
                    b.getHargaBeli(), b.getHargaJual(), b.getStok(), b.getSatuan(), b.getTanggalMasuk()});
        }
    }

    private void openForm(Barang barang) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Form Barang", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtKode = new JTextField(15);
        JTextField txtNama = new JTextField(15);
        JTextField txtKategori = new JTextField(15);
        JTextField txtHargaBeli = new JTextField(15);
        JTextField txtHargaJual = new JTextField(15);
        JTextField txtStok = new JTextField(15);
        JTextField txtSatuan = new JTextField(15);
        JTextField txtTglMasuk = new JTextField("yyyy-mm-dd", 15); // sederhana

        if (barang != null) {
            txtKode.setText(barang.getKodeBarang());
            txtNama.setText(barang.getNamaBarang());
            txtKategori.setText(barang.getKategori());
            txtHargaBeli.setText(String.valueOf(barang.getHargaBeli()));
            txtHargaJual.setText(String.valueOf(barang.getHargaJual()));
            txtStok.setText(String.valueOf(barang.getStok()));
            txtSatuan.setText(barang.getSatuan());
            txtTglMasuk.setText(barang.getTanggalMasuk() != null ? barang.getTanggalMasuk().toString() : "");
        }

        int y = 0;
        addField(dialog, gbc, "Kode Barang:", txtKode, y++);
        addField(dialog, gbc, "Nama Barang:", txtNama, y++);
        addField(dialog, gbc, "Kategori:", txtKategori, y++);
        addField(dialog, gbc, "Harga Beli:", txtHargaBeli, y++);
        addField(dialog, gbc, "Harga Jual:", txtHargaJual, y++);
        addField(dialog, gbc, "Stok:", txtStok, y++);
        addField(dialog, gbc, "Satuan:", txtSatuan, y++);
        addField(dialog, gbc, "Tgl Masuk:", txtTglMasuk, y++);

        JButton btnSave = new JButton("Simpan");
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        dialog.add(btnSave, gbc);

        btnSave.addActionListener(e -> {
            try {
                Barang b = new Barang();
                b.setKodeBarang(txtKode.getText());
                b.setNamaBarang(txtNama.getText());
                b.setKategori(txtKategori.getText());
                b.setHargaBeli(Double.parseDouble(txtHargaBeli.getText()));
                b.setHargaJual(Double.parseDouble(txtHargaJual.getText()));
                b.setStok(Integer.parseInt(txtStok.getText()));
                b.setSatuan(txtSatuan.getText());
                b.setTanggalMasuk(java.sql.Date.valueOf(txtTglMasuk.getText()));
                if (barang == null) {
                    barangDAO.insert(b);
                } else {
                    b.setIdBarang(barang.getIdBarang());
                    barangDAO.update(b);
                }
                dialog.dispose();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Input tidak valid: " + ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

    private void addField(JDialog dialog, GridBagConstraints gbc, String label, JTextField field, int y) {
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 1;
        dialog.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        dialog.add(field, gbc);
    }
}