package com.example.siakaduap.models;

import java.util.List;

public class BeritaResponse {
    private boolean status;
    private String message;
    private List<Berita> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Berita> getData() { return data; }
}
