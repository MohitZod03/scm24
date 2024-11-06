package com.porject.scm24.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.porject.scm24.entety.Contact;
import com.porject.scm24.entety.User;
// @Repository
// public interface ContactRepo extends JpaRepository<Contact , String> {

// // custom reposetory here ...

// // find contact by user id we use any of them ...
// //List<Contact> findByUser(User user);

// // this is for pagination ...
// Page<Contact> findByUser(User user , Pageable pageable);


// // because we not have user id that way writen querry..
// @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
// List<Contact> findByUserId(String userId );


// // this is method created for the serch contact 
//    // note :- all this is way need to write..

//          //this pattern is vimp used for the search the contact by name , email , phone number

// Page<Contact> findByUserAndNameContaining(User user ,String name , Pageable pageable);
// Page<Contact> findByUserAndEmailContaining(User user ,String email , Pageable pageable);
// Page<Contact> findByUserAndPhoneNumberContaining(User user ,String phone , Pageable pageable); 

// }


 

 

 
 

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    // find the contact by user
    // custom finder method
    Page<Contact> findByUser(User user, Pageable pageable);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
   // List<Contact> findByUserId(@Param("userId") String userId);

   List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndNameContaining(User user, String name, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String email, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phone, Pageable pageable);

}