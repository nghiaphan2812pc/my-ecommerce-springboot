package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.model.Response;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.JwtUtil;
import com.example.ecommerce.service.ValidateInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserRegisterLoginAPI {
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;
    @Autowired
    private JwtUtil jwtUtil;
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
        //Authenticate Username and Password
        try{
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        } catch (BadCredentialsException e){
            //Unauthenticated
            return new Response(Code.INCORRECT_USERNAME_PASSWORD, Message.INCORRECT_USERNAME_PASSWORD, null);
        }
        //Success authenticated
        //Response JWT token
        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return new Response(Code.SUCCESS, Message.SUCCESS, token);
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

        // Send verify mail link
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(registerRequest.getEmail());
        msg.setSubject("Success Registered");
        msg.setText(  "Click this link to verify your account: \n"  +  "http://localhost:8091/verifyRegister/" + jwtUtil.generateJWTForRegister(registerRequest));
        javaMailSender.send(msg);
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }

}
