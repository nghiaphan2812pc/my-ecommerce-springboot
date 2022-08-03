package com.example.ecommerce.controller_general;

import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserRepository userRepository;
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(){
        return "cart";
    }
    @RequestMapping(value = "/checkout",method = RequestMethod.GET)
    public String checkout(){
        return "checkout";
    }
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }
    @RequestMapping(value = "/successRegister",method = RequestMethod.GET)
    public String successRegister(){
        return "successRegisterPage";
    }
    @RequestMapping(value = "/successCheckout",method = RequestMethod.GET)
    public String successCheckout(){
        return "successCheckoutPage";
    }
    @RequestMapping(value = "/products",method = RequestMethod.GET)
    public String products(){
        return "products";
    }
//    @RequestMapping(value = "/verifyRegister/{token}")
//    public String verifyRegister(@PathVariable(value = "token")String token){
//        if(!jwtUtil.validateRegisterEmailToken(token)){
//            return "verifyFailed";
//        }
//        RegisterRequest request = jwtUtil.readRegisterToken(token);
//        //Success validate
//        //Add new user to DB
//        User user = new User(request.getUsername(),passwordEncoder.encode(request.getPassword()), request.getFullName(),request.getEmail(),request.getPhone(),request.getAddress(),"USER", AuthProvider.LOCAL);
//        userRepository.save(user);
//        return "successRegisterPage";
//    }
    @RequestMapping(value = "/pleaseVerifyEmailPage",method = RequestMethod.GET)
    public String pleaseVerifyEmail(){
        return "pleaseVerifyEmailPage";
    }
    @RequestMapping(value = "/productDetail" , method = RequestMethod.GET)
    public String productDetail(@RequestParam(name = "id")int id){
        return "product-details";
    }
    @RequestMapping(value = "/successLoginWithGoogle" , method = RequestMethod.GET)
    public String productDetail(@RequestParam(name = "token")String token){
        return "successLoginWithGoogle";
    }
    @RequestMapping(value = "/updateUserInfo" , method = RequestMethod.GET)
    public String updateUserInfo(){
        return "updateUserInfo";
    }
    @RequestMapping(value = "/forgetUsername" , method = RequestMethod.GET)
    public String forgetUsername(){
        return "forgetUsername";
    }
    @RequestMapping(value = "/forgetPassword" , method = RequestMethod.GET)
    public String forgetPassword(){
        return "forgetPassword";
    }
    @RequestMapping(value = "/resetPasswordPage",method = RequestMethod.GET)
    public String resetPasswordPage(@RequestParam(name = "reset-password-token")String token){
        return "resetPasswordPage";
    }
    @RequestMapping(value = "/changePasswordPage",method = RequestMethod.GET)
    public String changePasswordPage(){
        return "changePasswordPage";
    }
    @RequestMapping(value = "/searchProduct",method = RequestMethod.GET)
    public String searchProduct(@RequestParam(name = "key")String key){
        return "products";
    }
    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String errorPage(){return "error";}
}
