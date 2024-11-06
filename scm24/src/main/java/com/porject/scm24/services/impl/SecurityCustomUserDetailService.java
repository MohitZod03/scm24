package com.porject.scm24.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.porject.scm24.repository.UserRepository;

// this class purpose to check the email or useranme in database for authentication..

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {


// create object of userservice
 
@Autowired
private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      



      // apne user ko load karana he ...
      

      return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("user not found with this username " +username));

    }
}
