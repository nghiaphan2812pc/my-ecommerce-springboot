package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_product")
public class OrderProduct {
    @Id
    private String id;
    @Column
    private String order_id;
    @Column
    private int product_id;
    @Column
    private int quantity;
    @Column
    private String product_name;
    @Column
    private int unit_price;
    @Column
    private int total_price;
    public OrderProduct() {
    }

    public OrderProduct(String id,String order_id, int product_id, int quantity, String product_name, int unit_price, int total_price) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.product_name = product_name;
        this.unit_price = unit_price;
        this.total_price = total_price;
    }
}
