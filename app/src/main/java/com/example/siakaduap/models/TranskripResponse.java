package com.example.siakaduap.models;

import java.util.List;

public class TranskripResponse {
    private boolean status;
    private List<Khs> data;
    private double total_sks;
    private double ipk;

    public boolean isStatus() { return status; }
    public List<Khs> getData() { return data; }
    public double getTotalSks() { return total_sks; }
    public double getIpk() { return ipk; }
}
