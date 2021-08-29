package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;

    public RegisterRequest(String username, String password, String fullName, String email, String phone, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public RegisterRequest() {
    }
}
