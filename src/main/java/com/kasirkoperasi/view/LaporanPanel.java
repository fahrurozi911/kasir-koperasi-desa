package com.kasirkoperasi.view;

import com.kasirkoperasi.dao.LaporanDAO;
import com.kasirkoperasi.util.ExcelExporter;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class LaporanPanel extends JPanel {
    private LaporanDAO laporanDAO = new LaporanDAO();
    private JTable table;
    private DefaultTableModel tableModel;

    public LaporanPanel() {
        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Harian", createHarianPanel());
        tabbedPane.addTab("Bulanan", createBulananPanel());
        tabbedPane.addTab("Stok Barang", createStokPanel());
        tabbedPane.addTab("Barang Terlaris", createTerlarisPanel());

        // Tabel laporan
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(tabbedPane, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnExport = new JButton("Export Excel");
        btnExport.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".xlsx")) path += ".xlsx";
                ExcelExporter.exportTable(table, "Laporan", path);
            }
        });
        add(btnExport, BorderLayout.SOUTH);
    }

    private JPanel createHarianPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        JButton btnGenerate = new JButton("Tampilkan");
        btnGenerate.addActionListener(e -> {
            Date d = dateChooser.getDate();
            if (d == null) return;
            java.sql.Date sqlDate = new java.sql.Date(d.getTime());
            Vector<Vector<Object>> data = laporanDAO.getLaporanHarian(sqlDate);
            setTableModel(new String[]{"No Faktur", "Tanggal", "Total", "Bayar", "Kembalian", "Kasir"}, data);
        });
        panel.add(new JLabel("Tanggal:"));
        panel.add(dateChooser);
        panel.add(btnGenerate);
        return panel;
    }

    private JPanel createBulananPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JComboBox<Integer> cmbTahun = new JComboBox<>();
        for (int i = 2023; i <= 2030; i++) cmbTahun.addItem(i);
        JComboBox<Integer> cmbBulan = new JComboBox<>();
        for (int i = 1; i <= 12; i++) cmbBulan.addItem(i);
        JButton btnGenerate = new JButton("Tampilkan");
        btnGenerate.addActionListener(e -> {
            int tahun = (int) cmbTahun.getSelectedItem();
            int bulan = (int) cmbBulan.getSelectedItem();
            Vector<Vector<Object>> data = laporanDAO.getLaporanBulanan(tahun, bulan);
            setTableModel(new String[]{"No Faktur", "Tanggal", "Total", "Bayar", "Kembalian", "Kasir"}, data);
        });
        panel.add(new JLabel("Tahun:"));
        panel.add(cmbTahun);
        panel.add(new JLabel("Bulan:"));
        panel.add(cmbBulan);
        panel.add(btnGenerate);
        return panel;
    }

    private JPanel createStokPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnGenerate = new JButton("Tampilkan Stok");
        btnGenerate.addActionListener(e -> {
            Vector<Vector<Object>> data = laporanDAO.getLaporanStokBarang();
            setTableModel(new String[]{"Kode", "Nama Barang", "Kategori", "Stok", "Satuan"}, data);
        });
        panel.add(btnGenerate);
        return panel;
    }

    private JPanel createTerlarisPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JDateChooser dari = new JDateChooser();
        JDateChooser sampai = new JDateChooser();
        dari.setDateFormatString("yyyy-MM-dd");
        sampai.setDateFormatString("yyyy-MM-dd");
        JButton btnGenerate = new JButton("Tampilkan");
        btnGenerate.addActionListener(e -> {
            Date d1 = dari.getDate();
            Date d2 = sampai.getDate();
            if (d1 == null || d2 == null) return;
            java.sql.Date sql1 = new java.sql.Date(d1.getTime());
            java.sql.Date sql2 = new java.sql.Date(d2.getTime());
            Vector<Vector<Object>> data = laporanDAO.getBarangTerlaris(sql1, sql2);
            setTableModel(new String[]{"Nama Barang", "Total Terjual", "Total Pendapatan"}, data);
        });
        panel.add(new JLabel("Dari:"));
        panel.add(dari);
        panel.add(new JLabel("Sampai:"));
        panel.add(sampai);
        panel.add(btnGenerate);
        return panel;
    }

    private void setTableModel(String[] columns, Vector<Vector<Object>> data) {
        tableModel.setDataVector(data, new Vector<>(java.util.Arrays.asList(columns)));
    }
}