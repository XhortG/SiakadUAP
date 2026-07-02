package com.example.siakaduap.models;

public class Absensi {
    private String kode_mk;
    private String nama_mk;
    private int hadir;
    private int izin;
    private int sakit;
    private int alfa;
    private int total_pertemuan;

    public String getKodeMk() { return kode_mk; }
    public String getNamaMk() { return nama_mk; }
    public int getHadir() { return hadir; }
    public int getIzin() { return izin; }
    public int getSakit() { return sakit; }
    public int getAlfa() { return alfa; }
    public int getTotalPertemuan() { return total_pertemuan; }
    
    public void setHadir(int hadir) { this.hadir = hadir; }
    public void setIzin(int izin) { this.izin = izin; }
    public void setSakit(int sakit) { this.sakit = sakit; }
    public void setAlfa(int alfa) { this.alfa = alfa; }
}
