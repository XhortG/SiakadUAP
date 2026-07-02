package com.example.siakaduap.models;

public class Pembayaran {
    private String semester;
    private int tagihan;
    private String status;
    private String tanggal_bayar;

    public String getSemester() { return semester; }
    public int getTagihan() { return tagihan; }
    public String getStatus() { return status; }
    public String getTanggalBayar() { return tanggal_bayar; }
    
    public void setStatus(String status) { this.status = status; }
    public void setTanggalBayar(String tanggal_bayar) { this.tanggal_bayar = tanggal_bayar; }
}
