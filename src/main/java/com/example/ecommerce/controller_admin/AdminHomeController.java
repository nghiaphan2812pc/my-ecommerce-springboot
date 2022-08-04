package com.example.ecommerce.controller_admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminHomeController {
    @RequestMapping(value = "/adminHomePage",method = RequestMethod.GET)
    public String adminHomePage(){
        return "adminHomePage";
    }
}
