package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private int price;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "brand_id")
    private Brand brand;
    @Column
    private String description;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product_id", cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();
    public Product() {
    }

    public Product(String name, int price, Brand brand,String description) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.description = description;
    }
}
