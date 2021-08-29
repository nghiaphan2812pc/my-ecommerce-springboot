package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class UserInfo {
    private int id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;

    public UserInfo(int id, String username, String fullName, String email, String phone, String address) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public UserInfo() {
    }
}
