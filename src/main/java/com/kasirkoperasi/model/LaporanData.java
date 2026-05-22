package com.kasirkoperasi.model;

public class LaporanData {
    // Bisa digunakan untuk berbagai laporan, atribut dinamis sesuai query
    private Object[] kolom;
    private String[] namaKolom;

    public LaporanData(String[] namaKolom, Object[] kolom) {
        this.namaKolom = namaKolom;
        this.kolom = kolom;
    }
    public Object[] getKolom() { return kolom; }
    public String[] getNamaKolom() { return namaKolom; }
}