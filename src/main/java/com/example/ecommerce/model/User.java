package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column(name = "fullname")
    private String fullName;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String role;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user_id", cascade = CascadeType.ALL)
    private List<CartProduct> cart;

    public User() {
    }

    public User(String username, String password, String fullName, String email, String phone, String address, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }
}