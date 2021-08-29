package com.example.ecommerce.oauth2;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User auth2User = (CustomOAuth2User) authentication.getPrincipal();
        System.out.println(auth2User.getName());
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(auth2User.getEmail()));
        String token;
        //New user
        if(!optionalUser.isPresent()){
            User user = createNewUser(auth2User.getEmail(), auth2User.getName(),AuthProvider.GOOGLE);
            userRepository.save(user);
            token = jwtUtil.generateToken(user.getUsername());
        }
        //Existing user
        else {
            User user = optionalUser.get();
            token = jwtUtil.generateToken(user.getUsername());
        }
        this.setDefaultTargetUrl("/successLoginWithGoogle?token=" + token);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public User createNewUser(String email, String name, AuthProvider authProvider){
        String password = UUID.randomUUID().toString();
        return new User(email,passwordEncoder.encode(password), name, email,null, null, "USER", AuthProvider.GOOGLE);
    }
}
