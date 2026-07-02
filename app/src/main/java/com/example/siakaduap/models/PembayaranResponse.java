package com.example.siakaduap.models;
import java.util.List;
public class PembayaranResponse {
    private boolean status;
    private String message;
    private List<Pembayaran> data;
    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Pembayaran> getData() { return data; }
}
