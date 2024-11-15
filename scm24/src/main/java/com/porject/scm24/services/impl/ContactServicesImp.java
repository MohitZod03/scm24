package com.porject.scm24.services.impl;

import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.porject.scm24.entety.Contact;
import com.porject.scm24.entety.User;
import com.porject.scm24.helper.ResourceNotFoundException;
import com.porject.scm24.repository.ContactRepo;
import com.porject.scm24.services.ContactService;

@Service
public class ContactServicesImp implements ContactService {

    // get object of repository
   @Autowired
    private ContactRepo contactRepo;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(ContactServicesImp.class);


    @Override
    public List<Contact> getAllContact() {
        
       return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       
       return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found exception with this id: "+id));
    }

    @Override
    public Contact saveContact(Contact contact) {
        String contactId= UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
        
    }

    @Override
    public Contact updateContact(Contact contact) {
       
        var contactOld = contactRepo
        .findById(contact.getId())
        .orElseThrow(()-> new ResourceNotFoundException("Contact not found with this id: "+contact.getId()));

        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setDescription(contact.getDescription());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedinLink(contact.getLinkedinLink());
        contactOld.setPicture(contact.getPicture());

        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
          
      

        

        return contactRepo.save(contactOld);
       
    }



                  // this method for delete the contact
      @Override
     public void delete(String id) {
               
     var contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with this id: "+id));
               
                     contactRepo.delete(contact);
      }
               
               


   







    
    @Override
    public List<Contact> serchByUserId(String userId) {
       
       return contactRepo.findByUserId(userId);


    }

  
  
  
  
  

   //  @Override
   //  public List<Contact> getByUser(User user) {
      //  
      //  return contactRepo.findByUser(user);
   //  }

  
  
  
   // get all contacts of user with pagination

   
   @Override
   public Page<Contact> getByUser(User user, int page , int size,String sortBy,String direction) {

      Sort sort = direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

      var pageable = PageRequest.of(page, size);



         return contactRepo.findByUser(user, pageable);

   }
                // this all method that help to serch the contact.. and write and get from repoPage
   @Override
   public Page<Contact> serchContactByName(String name, int size, int page, String sortBy, String order,User user) {
// need to create pageable object
Sort sort = order.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
  var pageable = PageRequest.of(page, size,sort);
return contactRepo.findByUserAndNameContaining(user, name, pageable);

   }

   @Override
   public Page<Contact> serchContactsByEmail(String email, int size, int page, String sortBy, String order,User user) {
      Sort sort = order.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
      var pageable = PageRequest.of(page, size,sort);
          return contactRepo.findByUserAndEmailContaining(user, email, pageable);
   }

   @Override
   public Page<Contact> serchContactsByPhone(String phone, int size, int page, String sortBy, String order,User user) {
      Sort sort = order.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
      var pageable = PageRequest.of(page, size,sort);
      return contactRepo.findByUserAndPhoneNumberContaining(user, phone, pageable);
   }



 










}






