package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.dto.ChangePasswordRequest;
import com.example.ecommerce.dto.ResetPasswordRequest;
import com.example.ecommerce.dto.UserInfo;
import com.example.ecommerce.model.Response;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.EmailSenderService;
import com.example.ecommerce.service.JwtUtil;
import com.example.ecommerce.service.ValidateInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
class UserInfoAPI {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidateInputService validateInputService;
    @Autowired
    private EmailSenderService emailSenderService;
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public Response getUserInfo(@RequestParam(value ="Username")String username){
        if (username == null) {
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        User user = userRepository.findByUsername(username);
        int id = user.getId();
        String fullName = user.getFullName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String address = user.getAddress();
        UserInfo userInfo = new UserInfo(id, username, fullName, email, phone,address);
        //Response User-info
        return new Response(Code.SUCCESS, Message.SUCCESS, userInfo);
    }
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Response updateUserInfo(@RequestHeader(value = "update-user-info-token")String token, @RequestBody UserInfo userInfo){
        //Check empty request
        if(userInfo.getFullName().isEmpty() || userInfo.getPhone().isEmpty() || userInfo.getAddress().isEmpty()){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        int id = userInfo.getId();
        //Authenticate token
        if(!jwtUtil.validateTokenWithID(token, id)){
            return new Response(Code.UNAUTHENTICATED, Message.UNAUTHENTICATED, null);
        }
        String newFullName = userInfo.getFullName();
        String newPhone = userInfo.getPhone();
        String newAddress = userInfo.getAddress();

        //Get user
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.get();
        if(user.getPhone() != null) {
            if (!user.getPhone().equals(newPhone)) {
                //Validate Phone
                if (!validateInputService.validatePhone(newPhone)) {
                    return new Response(Code.INVALID_PHONE, Message.INVALID_PHONE, null);
                }

                //Validate duplicated Phone
                if (!validateInputService.validateDuplicatedPhone(newPhone)) {
                    return new Response(Code.DATA_DUPLICATED_PHONE, Message.DATA_DUPLICATED_PHONE, null);
                }
            }
        }
        //Update user info
        user.setFullName(newFullName);
        user.setPhone(newPhone);
        user.setAddress(newAddress);
        userRepository.save(user);
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/getUsernameViaEmail/{email}", method = RequestMethod.GET)
    public Response getUsernameInEmail(@PathVariable(value = "email")String email){
        //Validate email
        if(!validateInputService.validateEmail(email)){
           return new Response(Code.INVALID_EMAIL, Message.INVALID_EMAIL, null);
        }
        //Email not existing
        if(validateInputService.validateDuplicatedEmail(email)){
            return new Response(Code.NOT_FOUND_EMAIL, Message.NOT_FOUND_EMAIL, null);
        }
        //Send username via email
        User user = userRepository.findByEmail(email);
        emailSenderService.sendUsernameViaEmail(email, user.getUsername());
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/getPasswordReset/{email}",method = RequestMethod.GET)
    public Response getPasswordReset(@PathVariable(value = "email")String email){
        //Validate email
        if(!validateInputService.validateEmail(email)){
            return new Response(Code.INVALID_EMAIL, Message.INVALID_EMAIL, null);
        }
        //Email not existing
        if(validateInputService.validateDuplicatedEmail(email)){
            return new Response(Code.NOT_FOUND_EMAIL, Message.NOT_FOUND_EMAIL, null);
        }
        String token = jwtUtil.generateJWTForResetPassword(email);
        emailSenderService.sendPasswordReset(email, token);
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
    public Response resetPassword(@RequestHeader(name = "reset-password-token")String token, @RequestBody ResetPasswordRequest request){
        if(!jwtUtil.validateResetPasswordToken(token)){
            return new Response(Code.UNAUTHENTICATED, Message.UNAUTHENTICATED, null);
        }
        String password = request.getPassword();
        //Validate password
        if(!validateInputService.validatePassword(password)){
            return new Response(Code.INVALID_PASSWORD, Message.INVALID_PASSWORD, null);
        }
        String email = jwtUtil.readResetPasswordToken(token);
        User user = userRepository.findByEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Response changePassword(@RequestBody ChangePasswordRequest request, @RequestHeader(name = "Token")String token){
        int id = request.getUserID();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        //Check null request
        if(oldPassword == null || newPassword == null) return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        //Authenticate token
        if(!jwtUtil.validateTokenWithID(token, id)){
            return new Response(Code.UNAUTHENTICATED, Message.UNAUTHENTICATED, null);
        }
        //Check old password
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.get();
        //Validate new password
        if(!validateInputService.validatePassword(newPassword)){
            return new Response(Code.INVALID_PASSWORD, Message.INVALID_PASSWORD, null);
        }
        //Validate old password
        if(oldPassword.equals(user.getPassword())){
            return new Response(Code.INCORRECT_PASSWORD, Message.INCORRECT_PASSWORD, null);
        }
        //Change password
        user.setPassword(newPassword);
        userRepository.save(user);
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
}
