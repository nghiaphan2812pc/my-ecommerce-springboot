package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.model.Response;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ValidateInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserRegisterLoginAPI {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private ValidateInputService validateInputService;
    @Autowired
    private JavaMailSender javaMailSender;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response login(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        //Validate Request
        if(username == null || password == null){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public Response register(@RequestBody RegisterRequest registerRequest){
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String fullName = registerRequest.getFullName();
        String email = registerRequest.getEmail();
        String phone = registerRequest.getPhone();
        String address = registerRequest.getAddress();

        //Validate Request
        // Check null
        if(username == null || password == null || email == null || phone == null || address == null){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        //Validate Username
        if(!validateInputService.validateUsername(username)){
            return new Response(Code.INVALID_USERNAME, Message.INVALID_USERNAME, null);
        }
        if(!validateInputService.validateDuplicatedUsername(username)){
            return new Response(Code.DATA_DUPLICATED_USERNAME, Message.DATA_DUPLICATED_USERNAME, null);
        }
        //Validate password
        if(!validateInputService.validatePassword(password)){
            return new Response(Code.INVALID_PASSWORD, Message.INVALID_PASSWORD, null);
        }
        //Validate email
        if(!validateInputService.validateEmail(email)){
            return new Response(Code.INVALID_EMAIL, Message.INVALID_EMAIL, null);
        }
        if(!validateInputService.validateDuplicatedEmail(email)){
            return new Response(Code.DATA_DUPLICATED_EMAIL, Message.DATA_DUPLICATED_EMAIL, null);
        }
        //Validate Phone
        if(!validateInputService.validatePhone(phone)){
            return new Response(Code.INVALID_PHONE, Message.INVALID_PHONE, null);
        }
        if(!validateInputService.validateDuplicatedPhone(phone)){
            return new Response(Code.DATA_DUPLICATED_PHONE, Message.DATA_DUPLICATED_PHONE, null);
        }

        User user = new User(username, passwordEncoder.encode(password), fullName, email, phone, address,"USER");
        userRepository.save(user);

        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }

}
