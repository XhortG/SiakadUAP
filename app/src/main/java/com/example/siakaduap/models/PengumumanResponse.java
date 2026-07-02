package com.example.siakaduap.models;

import java.util.List;

public class PengumumanResponse {
    private boolean status;
    private String message;
    private List<Pengumuman> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Pengumuman> getData() { return data; }
}
