package com.example.ecommerce.service;

import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";
    private final long EXPIRATION_TIME = 864_000_000;
    static final long EXPIRATION_TIME_FOR_MAIL_VERIFY = 120_000;
    static final long EXPIRATION_TIME_FOR_RESET_PASSWORD = 240_000;

    @Autowired
    private UserRepository userRepository;

    public String readUsername(String token) {
        String username = null;
        try{
            username = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return null;
        }
        return username;
    }
    public Map<String, Object> readAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


    public String generateToken(String username){
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return JWT;
    }

    public boolean validateToken(String token){
        try{
            String username = readUsername(token);
            User user = userRepository.findByUsername(username);
            if(user == null) return false;
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean validateAdminToken(String token){
        try{
            String username = readUsername(token);
            User user = userRepository.findByUsername(username);
            return (user.getRole().toLowerCase().equals(username.toLowerCase()));
        }catch (Exception e){
            return false;
        }
    }
    public boolean validateTokenWithID(String token, int id){
        try{
            String username = readUsername(token);
            User user = userRepository.findByUsername(username);
            return (user.getId() == id);
        }catch (Exception e){
            return false;
        }
    }
    public String generateJWTForRegister(RegisterRequest registerRequest){
        String JWT = Jwts.builder()
                .setSubject(registerRequest.getUsername())
                .claim("username",registerRequest.getUsername())
                .claim("password",registerRequest.getPassword())
                .claim("email",registerRequest.getEmail())
                .claim("fullName",registerRequest.getFullName())
                .claim("phone",registerRequest.getPhone())
                .claim("address", registerRequest.getAddress())
                .setExpiration(new Date(System.currentTimeMillis() +
                        EXPIRATION_TIME_FOR_MAIL_VERIFY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return JWT;
    }
    public String generateJWTForResetPassword(String email){
        String JWT = Jwts.builder()
                .setSubject(email)
                .claim("email",email)
                .setExpiration(new Date(System.currentTimeMillis() +
                        EXPIRATION_TIME_FOR_RESET_PASSWORD))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return JWT;
    }
    public String readResetPasswordToken(String token){
        String email = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("email",String.class);
        return email;
    }

    public RegisterRequest readRegisterToken(String token){
        String username = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("username",String.class);
        String password = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("password",String.class);
        String name = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("fullName",String.class);
        String phone = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("phone",String.class);
        String email = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("email",String.class);
        String address = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("address",String.class);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setFullName(name);
        registerRequest.setEmail(email);
        registerRequest.setPhone(phone);
        registerRequest.setAddress(address);
        return registerRequest;
    }
    public boolean validateRegisterEmailToken(String token){
        try{
            RegisterRequest registerRequest = readRegisterToken(token);
            if (registerRequest != null)
                return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }
    public boolean validateResetPasswordToken(String token){
        try{
            String email = readResetPasswordToken(token);
            if (email != null)
                return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }
}
