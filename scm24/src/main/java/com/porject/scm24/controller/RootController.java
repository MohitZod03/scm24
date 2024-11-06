package com.porject.scm24.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.porject.scm24.entety.User;
import com.porject.scm24.helper.Helper;
import com.porject.scm24.services.UserServices;

@ControllerAdvice
public class RootController {


     private Logger logger = LoggerFactory.getLogger(UserController.class);
 @Autowired
 private UserServices userServices;


  @ModelAttribute  // WHEN USE THIS ANNOTATION TAHT INPACK ON HOLE CLASS CONTROLLER NOT ANOTHER CONTROLLER...
   public void  addLogedUserInformation(Model model , Authentication authentication) {


    // before login 
    if (authentication== null) {
        return;
        
    }

    // after the login 

    System.out.println("TESTTING ON CONSOLE DATA GET USING MODEL_ATTRIBUTES");
;
    String userEmail= Helper.getEmailOfLoggedUser(authentication);



    if (userEmail != null) {
        logger.info("User email: {}", userEmail);
    } else {
        logger.warn("Could not retrieve user email");
    }
    
    // GETTING DATA FROM DM using crate method in services and imp
    
User userDethailFetch = userServices.getUserByEmail(userEmail);

System.out.println(userDethailFetch);

    // then print this things ...
    System.out.println(userDethailFetch.getName());
    System.out.println(userDethailFetch.getEmail());
    

    // this data shered to view using the modle ..

    model.addAttribute("loggedUser",userDethailFetch);


   }


}
