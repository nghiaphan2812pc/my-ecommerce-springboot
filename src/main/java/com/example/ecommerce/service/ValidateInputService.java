package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateInputService {
    @Autowired
    private UserRepository userRepository;
    public boolean validateUsername(String username){
        return username.matches("[a-zA-Z0-9_@]{7,50}$");
    }
    public boolean validateDuplicatedUsername(String username){
        User user = userRepository.findByUsername(username);
        return user == null;
    }
    public boolean validatePassword(String password){
        return password.matches("[a-zA-Z0-9_-]{7,50}$");
    }
    public boolean validateEmail(String email){
        return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
    public boolean validateDuplicatedEmail(String email){
        User user = userRepository.findByEmail(email);
        return user == null;
    }
    public boolean validatePhone(String phone){
        return phone.matches("[0-9]{9,11}$");
    }
    public boolean validateDuplicatedPhone(String phone){
        User user = userRepository.findByPhone(phone);
        return user == null;
    }
}
