package com.example.ecommerce.repository;

import com.example.ecommerce.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_product WHERE user_id = :userID AND product_id = :productID",nativeQuery = true)
    int deleteCartProductByUser_idAndProduct_id(int userID, int productID);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cart_product WHERE id = :id",nativeQuery = true)
    int deleteCartProductById(int id);
    @Query(value = "SELECT SUM(cart_product.quantity * product.price) FROM mydatabase.cart_product INNER JOIN mydatabase.product ON cart_product.product_id = product.id WHERE cart_product.user_id = :userID", nativeQuery = true)
    int getTotalPriceInCartByUserID(int userID);
}
