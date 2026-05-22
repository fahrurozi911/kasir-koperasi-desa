package com.kasirkoperasi.dao;

import com.kasirkoperasi.util.DatabaseConnection;
import java.sql.*;
import java.util.Vector;

public class LaporanDAO {

    public Vector<Vector<Object>> getLaporanHarian(Date tanggal) {
        return getLaporanByDate("DATE(tanggal) = ?", tanggal);
    }

    public Vector<Vector<Object>> getLaporanBulanan(int tahun, int bulan) {
        String sql = "SELECT t.*, u.nama FROM transaksi t LEFT JOIN users u ON t.id_user=u.id " +
                     "WHERE YEAR(t.tanggal)=? AND MONTH(t.tanggal)=? ORDER BY t.tanggal";
        Vector<Vector<Object>> data = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tahun);
            ps.setInt(2, bulan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("no_faktur"));
                row.add(rs.getTimestamp("tanggal"));
                row.add(rs.getDouble("total"));
                row.add(rs.getDouble("bayar"));
                row.add(rs.getDouble("kembalian"));
                row.add(rs.getString("nama"));
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Vector<Vector<Object>> getLaporanByDate(String where, Date param) {
        String sql = "SELECT t.*, u.nama FROM transaksi t LEFT JOIN users u ON t.id_user=u.id " +
                     "WHERE " + where + " ORDER BY t.tanggal";
        Vector<Vector<Object>> data = new Vector<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, param);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("no_faktur"));
                row.add(rs.getTimestamp("tanggal"));
                row.add(rs.getDouble("total"));
                row.add(rs.getDouble("bayar"));
                row.add(rs.getDouble("kembalian"));
                row.add(rs.getString("nama"));
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Vector<Vector<Object>> getLaporanStokBarang() {
        Vector<Vector<Object>> data = new Vector<>();
        String sql = "SELECT kode_barang, nama_barang, kategori, stok, satuan FROM barang ORDER BY nama_barang";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("kode_barang"));
                row.add(rs.getString("nama_barang"));
                row.add(rs.getString("kategori"));
                row.add(rs.getInt("stok"));
                row.add(rs.getString("satuan"));
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public Vector<Vector<Object>> getBarangTerlaris(Date dari, Date sampai) {
        Vector<Vector<Object>> data = new Vector<>();
        String sql = "SELECT b.nama_barang, SUM(d.jumlah) as total_jual, SUM(d.subtotal) as total_pendapatan " +
                     "FROM detail_transaksi d " +
                     "JOIN barang b ON d.id_barang=b.id_barang " +
                     "JOIN transaksi t ON d.id_transaksi=t.id_transaksi " +
                     "WHERE DATE(t.tanggal) BETWEEN ? AND ? " +
                     "GROUP BY b.id_barang ORDER BY total_jual DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, dari);
            ps.setDate(2, sampai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("nama_barang"));
                row.add(rs.getInt("total_jual"));
                row.add(rs.getDouble("total_pendapatan"));
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}