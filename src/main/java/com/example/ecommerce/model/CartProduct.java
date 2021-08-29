package com.example.ecommerce.model;

import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Data
@Table(name = "cart_product")
public class CartProduct {
    @Id
    private int id;
    @Column
    private int user_id;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "product_id")
    private Product product;
    @Column
    private int quantity;


    public CartProduct(int user_id, Product product, int quantity) {
        this.user_id = user_id;
        this.product = product;
        this.quantity = quantity;
    }
    public CartProduct() {}
}
