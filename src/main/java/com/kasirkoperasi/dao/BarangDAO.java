package com.kasirkoperasi.dao;

import com.kasirkoperasi.model.Barang;
import com.kasirkoperasi.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang ORDER BY id_barang";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Barang b = new Barang();
                b.setIdBarang(rs.getInt("id_barang"));
                b.setKodeBarang(rs.getString("kode_barang"));
                b.setNamaBarang(rs.getString("nama_barang"));
                b.setKategori(rs.getString("kategori"));
                b.setHargaBeli(rs.getDouble("harga_beli"));
                b.setHargaJual(rs.getDouble("harga_jual"));
                b.setStok(rs.getInt("stok"));
                b.setSatuan(rs.getString("satuan"));
                b.setTanggalMasuk(rs.getDate("tanggal_masuk"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Barang getByKode(String kode) {
        String sql = "SELECT * FROM barang WHERE kode_barang=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Barang b = new Barang();
                b.setIdBarang(rs.getInt("id_barang"));
                b.setKodeBarang(rs.getString("kode_barang"));
                b.setNamaBarang(rs.getString("nama_barang"));
                b.setKategori(rs.getString("kategori"));
                b.setHargaBeli(rs.getDouble("harga_beli"));
                b.setHargaJual(rs.getDouble("harga_jual"));
                b.setStok(rs.getInt("stok"));
                b.setSatuan(rs.getString("satuan"));
                b.setTanggalMasuk(rs.getDate("tanggal_masuk"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Barang b) {
        String sql = "INSERT INTO barang(kode_barang,nama_barang,kategori,harga_beli,harga_jual,stok,satuan,tanggal_masuk) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setDouble(4, b.getHargaBeli());
            ps.setDouble(5, b.getHargaJual());
            ps.setInt(6, b.getStok());
            ps.setString(7, b.getSatuan());
            ps.setDate(8, b.getTanggalMasuk());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Barang b) {
        String sql = "UPDATE barang SET kode_barang=?, nama_barang=?, kategori=?, harga_beli=?, harga_jual=?, stok=?, satuan=?, tanggal_masuk=? WHERE id_barang=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKategori());
            ps.setDouble(4, b.getHargaBeli());
            ps.setDouble(5, b.getHargaJual());
            ps.setInt(6, b.getStok());
            ps.setString(7, b.getSatuan());
            ps.setDate(8, b.getTanggalMasuk());
            ps.setInt(9, b.getIdBarang());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM barang WHERE id_barang=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Barang> search(String keyword) {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE nama_barang LIKE ? OR kode_barang LIKE ? OR kategori LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Barang b = new Barang();
                b.setIdBarang(rs.getInt("id_barang"));
                b.setKodeBarang(rs.getString("kode_barang"));
                b.setNamaBarang(rs.getString("nama_barang"));
                b.setKategori(rs.getString("kategori"));
                b.setHargaBeli(rs.getDouble("harga_beli"));
                b.setHargaJual(rs.getDouble("harga_jual"));
                b.setStok(rs.getInt("stok"));
                b.setSatuan(rs.getString("satuan"));
                b.setTanggalMasuk(rs.getDate("tanggal_masuk"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStok(int idBarang, int stokBaru) {
        String sql = "UPDATE barang SET stok=? WHERE id_barang=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stokBaru);
            ps.setInt(2, idBarang);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}