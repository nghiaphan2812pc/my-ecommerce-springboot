package com.example.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "brand")
public class Brand {
    @Id
    private int id;
    @Column
    private String name;
    public Brand() {}
    public Brand(String name) {
        this.name = name;
    }
}
