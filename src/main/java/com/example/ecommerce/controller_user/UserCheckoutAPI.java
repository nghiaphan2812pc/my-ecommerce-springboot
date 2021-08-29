package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.filter.CheckoutWithNewInfoRequest;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.EmailSenderService;
import com.example.ecommerce.service.JwtUtil;
import com.example.ecommerce.service.ValidateInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@RestController
public class UserCheckoutAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private ValidateInputService validateInputService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailSenderService emailSender;
    @RequestMapping(value = "/checkoutWithDefaultInfo/{userID}",method = RequestMethod.GET)
    public Response checkoutWithDefaultInfo(@PathVariable(value = "userID")int id,@RequestHeader(value = "Token")String token){
        //Validate token
        if(!jwtUtil.validateTokenWithID(token,id)){
            return new Response(Code.UNAUTHENTICATED, Message.UNAUTHENTICATED, null);
        }
        //Validate userID
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            return new Response(Code.NOT_FOUND_USER, Message.NOT_FOUND_USER, null);
        }
        User user = optionalUser.get();
        //Check null address and phone
        if(user.getAddress() == null || user.getPhone()==null){
            return new Response(Code.NOT_FOUND_DEFAULT_ADDRESS_OR_PHONE, Message.NOT_FOUND_DEFAULT_ADDRESS_OR_PHONE,null);
        }
        //Validate cart
        List<CartProduct> cart = user.getCart();
        if(cart == null || cart.size()==0){
            return new Response(Code.EMPTY_CART, Message.EMPTY_CART, null);
        }
        //Generate random id
        String orderID = UUID.randomUUID().toString();

        int totalPrice = cartProductRepository.getTotalPriceInCartByUserID(user.getId());
        //Create new order
        Order order = new Order(orderID,user.getId(),user.getFullName(),user.getAddress(),user.getPhone(),totalPrice,0,totalPrice,new Date(Calendar.getInstance().getTime().getTime()),"Success Ordered",null);
        orderRepository.save(order);
        //Copy product from cart to order
        for(CartProduct cartProduct:cart){
            Optional<Product> optionalProduct = productRepository.findById(cartProduct.getProduct().getId());
            Product product = optionalProduct.get();
            OrderProduct orderProduct = new OrderProduct(UUID.randomUUID().toString(),orderID,product.getId(),cartProduct.getQuantity(),product.getName(),product.getPrice(),product.getPrice()*cartProduct.getQuantity());
            orderProductRepository.save(orderProduct);
            //Delete all product in cart
            cartProductRepository.deleteCartProductById(cartProduct.getId());
        }
        //Send order detail email
        try{
            emailSender.sendOrderDetailsEmail(cart, user.getFullName(),user.getEmail(),user.getAddress(),user.getPhone());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/checkoutWithNewInfo/{userID}", method = RequestMethod.POST)
    public Response checkoutWithNewInfo(@PathVariable(value = "userID")int id, @RequestBody CheckoutWithNewInfoRequest request,@RequestHeader(value = "Token")String token){
        Response response = new Response();
        //Validate token
        if(!jwtUtil.validateTokenWithID(token,id)){
            return new Response(Code.UNAUTHENTICATED, Message.UNAUTHENTICATED, null);
        }
        //Validate userID
        Optional<User> optionalUser = userRepository.findById(id);
        if(!optionalUser.isPresent()){
            return new Response(Code.NOT_FOUND_USER, Message.NOT_FOUND_USER, null);
        }
        User user = optionalUser.get();
        //Validate cart
        List<CartProduct> cart = user.getCart();
        if(cart == null || cart.size()==0){
            return new Response(Code.EMPTY_CART, Message.EMPTY_CART, null);
        }
        //Validate request
        //Get new info in request
        String fullName = request.getFullName();
        String phone = request.getPhone();
        String address = request.getAddress();
        String note = request.getNote();
        //Check null
        if(fullName == null || phone == null || address == null){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        //Validate phone
        if(!validateInputService.validatePhone(phone)) {
            return new Response(Code.INVALID_PHONE, Message.INVALID_PHONE, null);
        }
        //Generate random id
        String orderID = UUID.randomUUID().toString();
        int totalPrice = cartProductRepository.getTotalPriceInCartByUserID(user.getId());

        //Create new order
        Order order = new Order(orderID,user.getId(),fullName,address,phone,totalPrice,0,totalPrice,new Date(Calendar.getInstance().getTime().getTime()),"Success Ordered",note);
        orderRepository.save(order);
        //Copy product from cart to order
        for(CartProduct cartProduct:cart){
            Optional<Product> optionalProduct = productRepository.findById(cartProduct.getProduct().getId());
            Product product = optionalProduct.get();
            OrderProduct orderProduct = new OrderProduct(UUID.randomUUID().toString(),orderID,product.getId(),cartProduct.getQuantity(),product.getName(),product.getPrice(),product.getPrice()*cartProduct.getQuantity());
            orderProductRepository.save(orderProduct);
            //Delete all product in cart
            cartProductRepository.deleteCartProductById(cartProduct.getId());
        }
        //Send order detail email
        try{
            emailSender.sendOrderDetailsEmail(cart, request.getFullName(), user.getEmail(),request.getAddress(),request.getPhone());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
}
