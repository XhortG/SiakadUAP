package com.example.siakaduap.models;

public class User {
    private String id;
    private String username;
    private String email;
    private String nama_lengkap;
    private String npm;
    private String prodi;

    private String semester;
    private String fakultas;
    private String tahun_masuk;
    private String status_mahasiswa;

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getNama_lengkap() { return nama_lengkap; }
    public String getNpm() { return npm; }
    public String getProdi() { return prodi; }
    public String getSemester() { return semester; }
    public String getFakultas() { return fakultas; }
    public String getTahunMasuk() { return tahun_masuk; }
    public String getStatusMahasiswa() { return status_mahasiswa; }
}
