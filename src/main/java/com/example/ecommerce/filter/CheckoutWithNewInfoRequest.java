package com.example.ecommerce.filter;

import lombok.Data;

@Data
public class CheckoutWithNewInfoRequest {
    private String fullName;
    private String phone;
    private String address;
    private String note;

}
