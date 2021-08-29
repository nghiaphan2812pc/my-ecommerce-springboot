package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    @Column
    private int user_id;
    @Column
    private String full_name;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private int total_price;
    @Column
    private int shipping_price;
    @Column
    private int final_price;
    @Column
    private Date date;
    @Column
    private String status;
    @Column
    private String note;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order_id", cascade = CascadeType.ALL)
    private List<OrderProduct> productList;
    public Order() {
    }

    public Order(String id, int user_id, String full_name, String address, String phone, int total_price, int shipping_price, int final_price, Date date, String status,String note) {
        this.id = id;
        this.user_id = user_id;
        this.full_name = full_name;
        this.address = address;
        this.phone = phone;
        this.total_price = total_price;
        this.shipping_price = shipping_price;
        this.final_price = final_price;
        this.date = date;
        this.status = status;
        this.note = note;
    }
}
