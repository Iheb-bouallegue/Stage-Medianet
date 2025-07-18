package com.example.medianet.stagemedianet.dto;

public class ForgetPasswordRequest {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ForgetPasswordRequest(String email) {
        this.email = email;
    }

    public ForgetPasswordRequest() {
    }
}
