package com.example.ecommerce.model;

import com.example.ecommerce.oauth2.AuthProvider;
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
    @Column(name = "full_name")
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
    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
    public User() {
    }

    public User(String username, String password, String fullName, String email, String phone, String address, String role, AuthProvider authProvider) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.authProvider = authProvider;
    }
}