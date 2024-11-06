package com.porject.scm24;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.porject.scm24.services.EmailServices;

@SpringBootTest
class Scm24ApplicationTests {

   
   


    @Autowired
    private EmailServices emailServices;

    @Test
    void contextLoads() {
    }

    @Test // Add this annotation
    void sendEmailTest() {
        emailServices.sendEmail("mohitzod03@gmail.com", "Just managing  email services here", "scm project link");
    }
}