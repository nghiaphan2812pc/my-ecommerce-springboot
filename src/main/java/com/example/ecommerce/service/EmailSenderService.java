package com.example.ecommerce.service;

import com.example.ecommerce.constant.Config;
import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderProduct;
import com.example.ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendOrderDetailsEmail(List<CartProduct> cart, String fullName, String email, String address, String phone) throws MessagingException {
        String orderDetailEmail = "";
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean multipart = true;
        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        orderDetailEmail += "<h1> Successfully Ordered!</h1> <h2> Bill Information </h2> <strong>Name: </strong><p>" + fullName+ "</p><br><strong>Phone: </strong><p>" + phone + "</p><br><strong>Address: </strong><p>" + address + "</p><br>";
        orderDetailEmail += "<table  style=\"border: 1px solid #181919; width: 70%;text-align: center;background-color: #EBEBEB; \">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th>No.</th>\n" +
                "\t\t\t<th>Product Name</th>\n" +
                "\t\t\t<th>Quantity</th>\n" +
                "\t\t\t<th>Unit Price</th>\n" +
                "\t\t\t<th>Price</th>\n" +
                "\t\t</tr>\n";
        int i = 1;
        int total =0;
        for (CartProduct p: cart) {
            orderDetailEmail += "<tr><td>" + i++ +"</td><td>" + p.getProduct().getName() + "</td><td>" + p.getQuantity() + "</td><td>" + p.getProduct().getPrice() + "</td><td>" + p.getQuantity()*p.getProduct().getPrice()+ "</td></tr>";
            total += p.getQuantity() * p.getProduct().getPrice();
        }

        orderDetailEmail +=
                "\t\t<tr  style=\"background-color:  #181919;\">\n" +
                        "\t\t\t<td style=\"color: antiquewhite; font-weight: bold;\" colspan=\"4\">Shipping</td>\n" +
                        "\t\t\t<td style=\"color:#FFFFFF;\">  "    +   "free" +  "        </td>\n" +
                        "\t\t</tr>\n" ;
        orderDetailEmail +=
                "\t\t<tr  style=\"background-color: #181919;\">\n" +
                        "\t\t\t<td style=\"color: antiquewhite; font-weight: bold;\" colspan=\"4\">Total</td>\n" +
                        "\t\t\t<td style=\"color:#FFFFFF;\">  "    +  String.valueOf(total) +  "        </td>\n" +
                        "\t\t</tr>\n" +
                        "\t</table>";

        orderDetailEmail += "<h3><i>Thanks for using our services</i></h3>";
        message.setContent(orderDetailEmail, "text/html; charset=utf-8");
        helper.setTo(email);
        helper.setSubject("Success Order");
        javaMailSender.send(message);
    }
    public void sendUsernameViaEmail(String email, String username){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Get Username");
        msg.setText(  "Your username is: "  + username);
        javaMailSender.send(msg);
    }
    public void sendPasswordReset(String email, String token){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Reset password");
        msg.setText(  "Click to reset your password: " + Config.URL + "/resetPasswordPage?reset-password-token=" + token);
        javaMailSender.send(msg);
    }
}
