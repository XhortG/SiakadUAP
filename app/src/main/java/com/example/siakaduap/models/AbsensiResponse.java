package com.example.siakaduap.models;
import java.util.List;
public class AbsensiResponse {
    private boolean status;
    private String message;
    private List<Absensi> data;
    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Absensi> getData() { return data; }
}
