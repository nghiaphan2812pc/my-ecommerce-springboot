package com.example.ecommerce;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import com.example.ecommerce.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Date;
import java.util.*;


@SpringBootApplication
public class ECommerceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    ImageRepository imageRepository;
    @Override
    public void run(String... args) throws Exception {
//        Optional<Brand> optBrand = brandRepository.findById(3);
//        Brand brand = optBrand.get();
//        for(int i = 100; i < 150; i++){
//            Product product = new Product("Product"+ i, (i%10+1)*1000000, brand, "This is product " + i);
//            productRepository.save(product);
//            Image img1 = new Image("shop/product/details/classic-fusion-forte-dei-marmi-3.jpg", i);
//            Image img2 = new Image("shop/product/details/big-bang-unico-summer-sm-1.jpg", i);
//            imageRepository.save(img1);
//            imageRepository.save(img2);
//        }
   }

}
