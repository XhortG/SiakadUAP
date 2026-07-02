package com.example.siakaduap.models;

public class LoginResponse {
    private boolean status;
    private String message;
    private User data;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public User getData() { return data; }
}
