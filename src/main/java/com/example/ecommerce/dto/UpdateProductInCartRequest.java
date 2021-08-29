package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class UpdateProductInCartRequest {
    private int userID;
    private int productID;
    private int quantity;

    public UpdateProductInCartRequest() {
    }
    public UpdateProductInCartRequest(int userID, int productID, int quantity) {
        this.userID = userID;
        this.productID = productID;
        this.quantity = quantity;
    }
}
