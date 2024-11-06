package com.porject.scm24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.porject.scm24.entety.Contact;
import com.porject.scm24.services.ContactService;


@RestController
@RequestMapping("/api")
public class ApiController { 

   @Autowired
   private ContactService contactService;


   // from uri pass id that get into veriable contactId and return the contact data.    
@GetMapping("/contact/{contactId}")
public Contact getContact(@PathVariable String contactId){




return contactService.getById(contactId);



}
}