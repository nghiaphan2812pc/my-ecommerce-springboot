package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private int userID;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest(int userID, String oldPassword, String newPassword) {
        this.userID = userID;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ChangePasswordRequest() {
    }
}
