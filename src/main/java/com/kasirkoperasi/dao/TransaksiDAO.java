package com.kasirkoperasi.dao;

import com.kasirkoperasi.model.DetailTransaksi;
import com.kasirkoperasi.model.Transaksi;
import com.kasirkoperasi.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    // Simpan transaksi + detail
    public void saveTransaction(Transaksi t, List<DetailTransaksi> details) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Insert header
            String sqlTrans = "INSERT INTO transaksi(no_faktur, tanggal, total, bayar, kembalian, id_user) VALUES(?,?,?,?,?,?)";
            PreparedStatement psTrans = conn.prepareStatement(sqlTrans, Statement.RETURN_GENERATED_KEYS);
            psTrans.setString(1, t.getNoFaktur());
            psTrans.setTimestamp(2, t.getTanggal());
            psTrans.setDouble(3, t.getTotal());
            psTrans.setDouble(4, t.getBayar());
            psTrans.setDouble(5, t.getKembalian());
            psTrans.setInt(6, t.getIdUser());
            psTrans.executeUpdate();

            ResultSet rs = psTrans.getGeneratedKeys();
            int idTransaksi = 0;
            if (rs.next()) {
                idTransaksi = rs.getInt(1);
            }

            // Insert detail & update stok
            String sqlDetail = "INSERT INTO detail_transaksi(id_transaksi, id_barang, jumlah, subtotal) VALUES(?,?,?,?)";
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            for (DetailTransaksi d : details) {
                psDetail.setInt(1, idTransaksi);
                psDetail.setInt(2, d.getIdBarang());
                psDetail.setInt(3, d.getJumlah());
                psDetail.setDouble(4, d.getSubtotal());
                psDetail.addBatch();

                // Kurangi stok
                BarangDAO barangDAO = new BarangDAO();
                barangDAO.updateStok(d.getIdBarang(), getCurrentStok(conn, d.getIdBarang()) - d.getJumlah());
            }
            psDetail.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    private int getCurrentStok(Connection conn, int idBarang) throws SQLException {
        String sql = "SELECT stok FROM barang WHERE id_barang=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idBarang);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("stok");
        return 0;
    }

    public List<Transaksi> getAll() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY tanggal DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setIdTransaksi(rs.getInt("id_transaksi"));
                t.setNoFaktur(rs.getString("no_faktur"));
                t.setTanggal(rs.getTimestamp("tanggal"));
                t.setTotal(rs.getDouble("total"));
                t.setBayar(rs.getDouble("bayar"));
                t.setKembalian(rs.getDouble("kembalian"));
                t.setIdUser(rs.getInt("id_user"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DetailTransaksi> getDetailByTransaksi(int idTransaksi) {
        List<DetailTransaksi> list = new ArrayList<>();
        String sql = "SELECT d.*, b.nama_barang, b.harga_jual FROM detail_transaksi d " +
                     "JOIN barang b ON d.id_barang = b.id_barang WHERE d.id_transaksi=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetailTransaksi d = new DetailTransaksi();
                d.setIdDetail(rs.getInt("id_detail"));
                d.setIdTransaksi(rs.getInt("id_transaksi"));
                d.setIdBarang(rs.getInt("id_barang"));
                d.setJumlah(rs.getInt("jumlah"));
                d.setSubtotal(rs.getDouble("subtotal"));
                d.setNamaBarang(rs.getString("nama_barang"));
                d.setHargaJual(rs.getDouble("harga_jual"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}