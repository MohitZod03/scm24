package com.porject.scm24.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.porject.scm24.services.EmailServices;

import org.springframework.beans.factory.annotation.Value;
@Service
public class EmailServicesImp implements EmailServices {
    @Autowired
   private JavaMailSender mailSender;

 // dynamically get the domain from send with help of app.prop
 @Value("${spring.mail.properties.domain_name}")
 private String domainName;


    @Override
    public void sendEmail(String to, String subject, String body) {
       
      // to set email using this class

      SimpleMailMessage mailMessage = new SimpleMailMessage();

      mailMessage.setTo(to);
      mailMessage.setSubject(subject);
      mailMessage.setText(body);
      mailMessage.setFrom(domainName);

      mailSender.send(mailMessage);
      
    }

    @Override
    public void sendEmailWithHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithHtml'");
    }

    @Override
    public void sendEmailWithAttachment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWithAttachment'");
    }

}
