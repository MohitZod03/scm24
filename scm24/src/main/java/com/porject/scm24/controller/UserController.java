package com.porject.scm24.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.porject.scm24.services.UserServices;



@Controller
@RequestMapping("/user")
public class UserController {

//  @ModelAttribute  // WHEN USE THIS ANNOTATION TAHT INPACK ON HOLE CLASS CONTROLLER NOT ANOTHER CONTROLLER...
//    public void  addLogedUserInformation(Model model , Authentication authentication) {

//     System.out.println("TESTTING ON CONSOLE DATA GET USING MODEL_ATTRIBUTES");
// ;
//     String userEmail= Helper.getEmailOfLoggedUser(authentication);



//     if (userEmail != null) {
//         logger.info("User email: {}", userEmail);
//     } else {
//         logger.warn("Could not retrieve user email");
//     }
    
//     // GETTING DATA FROM DM using crate method in services and imp
    
// User userDethailFetch = userServices.getUserByEmail(userEmail);

//     // then print this things ...
//     System.out.println(userDethailFetch.getName());
//     System.out.println(userDethailFetch.getEmail());
    

//     // this data shered to view using the modle ..

//     model.addAttribute("loggedUser",userDethailFetch);


//    }





    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServices userServices;

    // user dashbaord page

    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // user profile page

    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication ) {


        return "user/profile";
    }


    

    // user add contacts page

    // user view contacts

    // user edit contact

    // user delete contact

}