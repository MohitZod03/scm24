package com.porject.scm24.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.porject.scm24.entety.User;
import com.porject.scm24.helper.AppConstain;
import com.porject.scm24.helper.Helper;
import com.porject.scm24.helper.ResourceNotFoundException;
import com.porject.scm24.repository.UserRepository;
import com.porject.scm24.services.EmailServices;
import com.porject.scm24.services.UserServices;

@Service
public class UserServiceImple implements UserServices {


    // for the password encoder...
    @Autowired
    private PasswordEncoder passwordEncoder;


  // to set verification link when user is saved 
  @Autowired
  private EmailServices emailServices;

    @Autowired
    private UserRepository userRepository;


    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public User savUser(User user) {
       
          /// genrated user id randomely....
        String userId=UUID.randomUUID().toString();
            user.setUserId(userId);

            /// password encoder for  user when authenticate..
            // set password..

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            // set the roles for the user ... // this get from helper/ AppConstrain class just for not herd code 

            user.setRoleList(List.of(AppConstain.ROLE_USER));


       // create random string that use as verification token 
       String emailToken= UUID.randomUUID().toString();

       // after the get token token save into db..
       user.setEmailToken(emailToken);
       User savedUser= userRepository.save(user);


       String emailLink= Helper.getLinkForEmailVerification(emailToken);
       
       emailServices.sendEmail(savedUser.getEmail(), "verify Email : Email Contact Manager ", emailLink);

return savedUser;
   

}




    @Override
    public Optional<User> getUserById(String id) {
       
        return userRepository.findById(id);
       
    }

    @Override
    public Optional<User> updatUser(User user) {
     User user2=userRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found exception.."));

     // updating user2 from user..


     user2.setName(user.getName());
     user2.setEmail(user.getEmail());
     user2.setPassword(user.getPassword());
     user2.setAbout(user.getAbout());
     user2.setPhoneNumber(user.getPhoneNumber());
     user2.setProfilePic(user.getProfilePic());

     user2.setEnabled(user.isEnabled());
     user2.setEmailVerified(user.isEmailVerified());
     user2.setPhoneVerified(user.isPhoneVerified());
     user2.setProvider(user.getProvider());
     user2.setProviderUserId( user.getProviderUserId());

// save user in database ...
    User user3 = userRepository.save(user2);
   return Optional.ofNullable(user3);

 }

    @Override
    public void deleteUser(String id) {
        User user2=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found exception"));
        userRepository.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        
        User user=userRepository.findById(userId).orElse(null);
        return user!=null?true:false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        
      User user=  userRepository.findByEmail(email).orElse(null);
      return  user!=null?true:false;
        
    }

    @Override
    public List<User> getAllUsers() {
       
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        
        return userRepository.findByEmail(email).orElse(null);
    }



}
