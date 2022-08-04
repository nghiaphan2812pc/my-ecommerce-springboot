package com.example.ecommerce.controller_admin;

import com.example.ecommerce.constant.Code;
import com.example.ecommerce.constant.Message;
import com.example.ecommerce.model.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminRestController {
    @RequestMapping(value = "/adminAPI",method = RequestMethod.GET)
    public Response adminHomePage(){
        return new Response(Code.SUCCESS, Message.SUCCESS, null);
    }
}
