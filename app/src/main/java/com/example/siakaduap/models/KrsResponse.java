package com.example.siakaduap.models;

import java.util.List;

public class KrsResponse {
    private boolean status;
    private String message;
    private List<Krs> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Krs> getData() { return data; }
}
