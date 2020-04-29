package com.techbek.auth0jwt.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailSenderUtil {
    @Autowired
    JavaMailSender javaMailSender;
    SimpleMailMessage msg ;
    public void sendWelcomeEmail(String email) {

        msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom("uzucorp");
        msg.setSubject("Welcome To Auth0");
        msg.setText("Hello "+email+"\n Welcome To Our Website");
        long before = System.currentTimeMillis();
        javaMailSender.send(msg);
        long now = System.currentTimeMillis()-before;
        log.info("Email Sent took "+now+"s");

    }

    public void sendResetToken(String email, String uuid) {
        msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom("solutions.techbek@gmail.com");
        msg.setSubject("Email Reset");
//        Send back a ResetEmailRequest Object to change password
        msg.setText(String.format("Reset Email by Passing a Pojo of %n Email %s %n Verification Token %s %n New Password : ?", email, uuid));
        long before = System.currentTimeMillis();
        javaMailSender.send(msg);
        long now = System.currentTimeMillis()-before;
        log.info("Email Sent took "+now+"s");
    }
}
