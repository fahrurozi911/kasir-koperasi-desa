package com.kasirkoperasi.view;

import com.kasirkoperasi.dao.TransaksiDAO;
import com.kasirkoperasi.model.DetailTransaksi;
import com.kasirkoperasi.model.Transaksi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RiwayatTransaksiPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TransaksiDAO transaksiDAO = new TransaksiDAO();

    public RiwayatTransaksiPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Riwayat Transaksi");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] col = {"ID", "No Faktur", "Tanggal", "Total", "Bayar", "Kembalian"};
        tableModel = new DefaultTableModel(col, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnDetail = new JButton("Lihat Detail");
        btnDetail.addActionListener(e -> showDetail());
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> refreshData());

        JPanel bottom = new JPanel();
        bottom.add(btnDetail);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        refreshData();
    }

    private void refreshData() {
        List<Transaksi> list = transaksiDAO.getAll();
        tableModel.setRowCount(0);
        for (Transaksi t : list) {
            tableModel.addRow(new Object[]{t.getIdTransaksi(), t.getNoFaktur(), t.getTanggal(), t.getTotal(), t.getBayar(), t.getKembalian()});
        }
    }

    private void showDetail() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi terlebih dahulu!");
            return;
        }
        int idTransaksi = (int) tableModel.getValueAt(row, 0);
        List<DetailTransaksi> details = transaksiDAO.getDetailByTransaksi(idTransaksi);

        String[] col = {"Nama Barang", "Harga", "Jumlah", "Subtotal"};
        DefaultTableModel detailModel = new DefaultTableModel(col, 0);
        for (DetailTransaksi d : details) {
            detailModel.addRow(new Object[]{d.getNamaBarang(), d.getHargaJual(), d.getJumlah(), d.getSubtotal()});
        }
        JTable detailTable = new JTable(detailModel);
        JOptionPane.showMessageDialog(this, new JScrollPane(detailTable), "Detail Transaksi", JOptionPane.PLAIN_MESSAGE);
    }
}