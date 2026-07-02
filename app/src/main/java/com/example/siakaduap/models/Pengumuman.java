package com.example.siakaduap.models;

public class Pengumuman {
    private int id;
    private String judul;
    private String isi;
    private String tanggal;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getIsi() { return isi; }
    public void setIsi(String isi) { this.isi = isi; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
}
