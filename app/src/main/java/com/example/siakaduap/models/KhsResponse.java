package com.example.siakaduap.models;

import java.util.List;

public class KhsResponse {
    private boolean status;
    private String message;
    private List<Khs> data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public List<Khs> getData() { return data; }
}
