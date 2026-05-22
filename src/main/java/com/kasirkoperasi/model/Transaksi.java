package com.kasirkoperasi.model;

import java.sql.Timestamp;

public class Transaksi {
    private int idTransaksi;
    private String noFaktur;
    private Timestamp tanggal;
    private double total;
    private double bayar;
    private double kembalian;
    private int idUser;

    public Transaksi() {}

    // getters and setters
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public String getNoFaktur() { return noFaktur; }
    public void setNoFaktur(String noFaktur) { this.noFaktur = noFaktur; }
    public Timestamp getTanggal() { return tanggal; }
    public void setTanggal(Timestamp tanggal) { this.tanggal = tanggal; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public double getBayar() { return bayar; }
    public void setBayar(double bayar) { this.bayar = bayar; }
    public double getKembalian() { return kembalian; }
    public void setKembalian(double kembalian) { this.kembalian = kembalian; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
}