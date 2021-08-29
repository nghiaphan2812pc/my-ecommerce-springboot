package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaginationServiceImpl implements IPaginationService {
    @Autowired
    ProductRepository proRepository;

    @Override
    public Page<Product> findAllProductPagination(int pageNumber, int pageSize, int brandID, int minPrice, int maxPrice) {
        if (brandID <= 0) {
            if (minPrice <= 0 && maxPrice <= 0) {
                Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
                Page<Product> page = proRepository.findAll(pageable);
                return page;
            } else {
                Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
                Page<Product> page = proRepository.findProductByPrice(minPrice, maxPrice, pageable);
                return page;
            }
        } else {
            if ((minPrice <= 0 && maxPrice <= 0) || (minPrice > maxPrice)) {
                Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
                Page<Product> page = proRepository.findProductByBrand(brandID, pageable);
                return page;
            } else {
                Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
                Page<Product> page = proRepository.findProductsFullFilter(brandID, minPrice, maxPrice, pageable);
                return page;
            }
        }
    }
}
