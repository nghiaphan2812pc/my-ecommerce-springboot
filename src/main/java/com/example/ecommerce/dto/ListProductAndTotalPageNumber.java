package com.example.ecommerce.dto;

import com.example.ecommerce.model.Product;
import lombok.Data;
import java.util.List;
@Data
public class ListProductAndTotalPageNumber {
    private List<Product> listProduct;
    private int totalPageNumber;

    public ListProductAndTotalPageNumber() {
    }

    public ListProductAndTotalPageNumber(List<Product> listProduct, int totalPageNumber) {
        this.listProduct = listProduct;
        this.totalPageNumber = totalPageNumber;
    }
}
