package com.example.siakaduap.models;

import java.util.List;

public class MkTersediaResponse {
    private boolean status;
    private List<MataKuliah> data;

    public boolean isStatus() { return status; }
    public List<MataKuliah> getData() { return data; }
}
