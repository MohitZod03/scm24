package com.porject.scm24.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.porject.scm24.entety.Contact;
import com.porject.scm24.entety.User;

public interface ContactService {

    

// get all contacts

List<Contact> getAllContact();

// get contacts by id
Contact getById(String id);

// save the contacts here 
Contact saveContact(Contact contact);

// update the contact..
Contact updateContact(Contact contact);

// delet contact.

  void delete(String id);
// serch contact by id 
              
                // this is way that serch contact by name ..
Page<Contact> serchContactByName(String name, int size, int page ,String sortBy,String order,User user);

                 // this is contact serch by the email...
Page<Contact> serchContactsByEmail(String email, int size, int page ,String sortBy,String order,User user);
                  // this is contact serch by the phone..., int size, int page ,Stri
 Page<Contact> serchContactsByPhone(String phone, int size, int page ,String sortBy,String order,User user);
                

// get contact by userid
List<Contact> serchByUserId(String userId);

// get all contacts of user

//List<Contact> getByUser(User user);  



// get all contacts of user with pagination
Page<Contact> getByUser(User user , int page , int size,String sortBy,String direction);

}








