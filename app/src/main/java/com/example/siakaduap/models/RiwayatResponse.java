package com.example.siakaduap.models;
import java.util.List;
public class RiwayatResponse {
    private boolean status;
    private String message;
    private List<Riwayat> data;
    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Riwayat> getData() { return data; }
}
