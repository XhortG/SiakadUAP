package com.example.siakaduap.models;
import java.util.List;
public class JadwalResponse {
    private boolean status;
    private String message;
    private List<Jadwal> data;
    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Jadwal> getData() { return data; }
}
