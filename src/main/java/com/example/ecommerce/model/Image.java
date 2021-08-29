package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    private int id;
    @Column
    private String image;
    @Column
    private int product_id;

    public Image() {}

    public Image(String image, int product_id) {
        this.image = image;
        this.product_id = product_id;
    }
}

