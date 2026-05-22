package com.kasirkoperasi.model;

import java.sql.Date;

public class Barang {
    private int idBarang;
    private String kodeBarang;
    private String namaBarang;
    private String kategori;
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private String satuan;
    private Date tanggalMasuk;

    public Barang() {}

    // Full constructor, getters, setters
    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }
    public String getKodeBarang() { return kodeBarang; }
    public void setKodeBarang(String kodeBarang) { this.kodeBarang = kodeBarang; }
    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public double getHargaBeli() { return hargaBeli; }
    public void setHargaBeli(double hargaBeli) { this.hargaBeli = hargaBeli; }
    public double getHargaJual() { return hargaJual; }
    public void setHargaJual(double hargaJual) { this.hargaJual = hargaJual; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }
    public Date getTanggalMasuk() { return tanggalMasuk; }
    public void setTanggalMasuk(Date tanggalMasuk) { this.tanggalMasuk = tanggalMasuk; }
}