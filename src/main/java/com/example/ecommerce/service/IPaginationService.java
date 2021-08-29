package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;


public interface IPaginationService {
    Page<Product> findAllProductPagination(int pageNumber, int pageSize, int brandID, int minPrice, int maxPrice);
}
