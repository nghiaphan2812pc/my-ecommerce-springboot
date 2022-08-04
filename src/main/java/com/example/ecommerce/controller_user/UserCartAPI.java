package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.dto.UpdateProductInCartRequest;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.CartProductRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserCartAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartProductRepository cartProductRepository;

    @RequestMapping(value = "/getCart/{userID}", method = RequestMethod.GET)
    public Response getCart(@PathVariable(value = "userID")int userID){
        //Validate userID
        Optional<User> user = userRepository.findById(userID);
        if(!user.isPresent()){
            return new Response(Code.NOT_FOUND_USER, Message.NOT_FOUND_USER, null);
        }
        //Get cart
        List<CartProduct> cart = user.get().getCart();
        return new Response(Code.SUCCESS, Message.SUCCESS, cart);
    }
    @RequestMapping(value = "/addProductToCart", method = RequestMethod.POST)
    public Response addProductToCart(@RequestBody UpdateProductInCartRequest request){
        //Validate request
        int userID, productID, quantity;
        try{
            userID = request.getUserID();
            productID = request.getProductID();
            quantity = request.getQuantity();
        }catch (Exception e){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }

        //Validate quantity
        if(quantity <= 0){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        //Validate userID
        Optional<User> user = userRepository.findById(userID);
        if(!user.isPresent()){
            return new Response(Code.NOT_FOUND_USER, Message.NOT_FOUND_USER, null);
        }
        //Validate productID
        Optional<Product> product = productRepository.findById(productID);
        if(!product.isPresent()){
            return new Response(Code.NOT_FOUND_PRODUCT, Message.NOT_FOUND_PRODUCT, null);
        }
        //Success validate
        //Add product to cart
        List<CartProduct> cart = user.get().getCart();
        boolean existing = false;
        for(CartProduct cartProduct:cart){
            if(cartProduct.getProduct().getId() == request.getProductID()){
                cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                cartProductRepository.save(cartProduct);
                existing = true;
                break;
            }
        }
        if(!existing){
            Optional<Product> optionalProduct = productRepository.findById(productID);
            Product product1 = optionalProduct.get();
            cartProductRepository.save(new CartProduct(userID,product1,quantity));
        }
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/removeProductFromCart",method = RequestMethod.POST)
    public Response removeProductFromCart(@RequestBody UpdateProductInCartRequest request){
        //Validate request
        int userID, productID;
        try{
            userID = request.getUserID();
            productID = request.getProductID();
        }catch (Exception e){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }

        //Validate userID
        Optional<User> user = userRepository.findById(userID);
        if(!user.isPresent()){
            return new Response(Code.NOT_FOUND_USER, Message.NOT_FOUND_USER, null);
        }
        //Validate productID
        Optional<Product> product = productRepository.findById(productID);
        if(!product.isPresent()){
            return new Response(Code.NOT_FOUND_PRODUCT, Message.NOT_FOUND_PRODUCT, null);
        }
        //Success validate
        //Remove product from cart
        int updatedRecord = cartProductRepository.deleteCartProductByUser_idAndProduct_id(userID,productID);
        if(updatedRecord <1){
            return new Response(Code.NOT_FOUND_PRODUCT_IN_CART, Message.NOT_FOUND_PRODUCT_IN_CART, null);
        }
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/updateProductInCart")
    public Response updateProductInCart(@RequestBody UpdateProductInCartRequest request){
        //Validate request
        int userID, productID, quantity;
        try{
            userID = request.getUserID();
            productID = request.getProductID();
            quantity = request.getQuantity();
        }catch (Exception e){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }

        //Validate quantity
        if(quantity < 0){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        if(quantity == 0) return removeProductFromCart(request);
        //Validate userID
        Optional<User> user = userRepository.findById(userID);
        if(!user.isPresent()){
            return new Response(Code.NOT_FOUND_USER, Message.NOT_FOUND_USER, null);
        }
        //Validate productID
        Optional<Product> product = productRepository.findById(productID);
        if(!product.isPresent()){
            return new Response(Code.NOT_FOUND_PRODUCT, Message.NOT_FOUND_PRODUCT, null);
        }
        //Success validate
        //Update product in cart
        List<CartProduct> cart = user.get().getCart();
        boolean existing = false;
        for(CartProduct cartProduct:cart){
            if(cartProduct.getProduct().getId() == request.getProductID()){
                cartProduct.setQuantity(quantity);
                cartProductRepository.save(cartProduct);
                existing = true;
                break;
            }
        }
        if(!existing){
            Optional<Product> optionalProduct = productRepository.findById(productID);
            Product product1 = optionalProduct.get();
            cartProductRepository.save(new CartProduct(userID,product1,quantity));
        }
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
}
