package com.kasirkoperasi.model;

public class DetailTransaksi {
    private int idDetail;
    private int idTransaksi;
    private int idBarang;
    private int jumlah;
    private double subtotal;

    // Barang info untuk tampilan
    private String namaBarang;
    private double hargaJual;

    public DetailTransaksi() {}

    // getters and setters
    public int getIdDetail() { return idDetail; }
    public void setIdDetail(int idDetail) { this.idDetail = idDetail; }
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public double getHargaJual() { return hargaJual; }
    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }
}