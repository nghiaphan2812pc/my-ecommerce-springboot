package com.example.ecommerce.repository;

import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT * FROM product where price >= :minPrice && price <= :maxPrice",nativeQuery = true)
    Page<Product> findProductByPrice(int minPrice, int maxPrice, Pageable pageable);

    @Query(value = "SELECT * FROM product where brand_id = :brandID",nativeQuery = true)
    Page<Product> findProductByBrand(int brandID, Pageable pageable);

    @Query(value = "SELECT * FROM product where brand_id = :brandID && price >= :minPrice && price <= :maxPrice",nativeQuery = true)
    Page<Product> findProductsFullFilter(int brandID, int minPrice, int maxPrice, Pageable pageable);
    List<Product> findAllByNameContains(String key);
}
