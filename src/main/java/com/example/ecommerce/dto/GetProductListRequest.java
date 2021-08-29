package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class GetProductListRequest {
    private int brandID;
    private int minPrice;
    private int maxPrice;
    private int productNumberPerPage;
    private int pageNumber;

    public GetProductListRequest() {
    }

    public GetProductListRequest(int brandID, int minPrice, int maxPrice, int productNumberPerPage, int pageNumber) {
        this.brandID = brandID;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.productNumberPerPage = productNumberPerPage;
        this.pageNumber = pageNumber;
    }
}
