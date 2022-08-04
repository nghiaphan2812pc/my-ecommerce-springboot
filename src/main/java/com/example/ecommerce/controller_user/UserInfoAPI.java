package com.example.ecommerce.controller_user;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.dto.ChangePasswordRequest;
import com.example.ecommerce.dto.ResetPasswordRequest;
import com.example.ecommerce.dto.UserInfo;
import com.example.ecommerce.model.Response;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ValidateInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
class UserInfoAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidateInputService validateInputService;

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
    public Response updateUserInfo(@RequestBody UserInfo userInfo){
        //Check empty request
        if(userInfo.getFullName().isEmpty() || userInfo.getPhone().isEmpty() || userInfo.getAddress().isEmpty()){
            return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        }
        int id = userInfo.getId();

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

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Response changePassword(@RequestBody ChangePasswordRequest request){
        int id = request.getUserID();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        //Check null request
        if(oldPassword == null || newPassword == null) return new Response(Code.INVALID_DATA, Message.INVALID_DATA, null);
        //Check old password
        Optional<User> optUser = userRepository.findById(id);
        User user = optUser.get();
        //Validate new password
        if(!validateInputService.validatePassword(newPassword)){
            return new Response(Code.INVALID_PASSWORD, Message.INVALID_PASSWORD, null);
        }
        //Validate old password
        if(!oldPassword.equals(user.getPassword())){
            return new Response(Code.INCORRECT_PASSWORD, Message.INCORRECT_PASSWORD, null);
        }
        //Change password
        user.setPassword(newPassword);
        userRepository.save(user);
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
}
