// package com.porject.scm24.helper;


// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Component;


// @Component
// public class Helper {

//     public static String getEmailOfLoggedUser(Authentication authentication){
  
//        // AuthenticationPrincipal principal =(AuthenticationPrincipal)authentication.getPrincipal();

    

// // if login with email and password then find email
// if (authentication instanceof OAuth2AuthenticationToken) {
    
// // find token first
// var aOauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
// var clientResgisterId= aOauth2AuthenticationToken.getAuthorizedClientRegistrationId();

// // this is hold user email when login...
// String username ="";
// var oAuth2UserEmail= (OAuth2User)authentication.getPrincipal();

//    if(clientResgisterId.equalsIgnoreCase("google")){
//     // sign in with google..
//     System.out.println("getting email from google when login...");

// /// know find the email from principle of 
  

 
// username=oAuth2UserEmail.getAttribute("email").toString();


//     }else if (clientResgisterId.equalsIgnoreCase("github")) {
    
// System.out.println("getting email from the github ...");

// username=oAuth2UserEmail.getAttribute("email") != null ? oAuth2UserEmail.getAttribute("email").toString() : 
// oAuth2UserEmail.getAttribute("login").toString() + "@github.com";

// }
// //sigin with facebook
// return username;

// }else{
//     System.out.println("getting data from the local databaase");
//     return authentication.getName();
// }

        
//     }

// }




///                     THIS IS HOLE CLASS PURPOSE GET EMAIL OR USERNAME WHEN USER LOGIN...


package com.porject.scm24.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Helper {

    // THIS IS LOGGER TO GIVE LOG ON CONSOLE ACCORDING TO ACTION..
    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    public static String getEmailOfLoggedUser(Authentication authentication) {



        // IF NOT AUTHENTICATE BY ANY THING..
        if (authentication == null) {
            logger.warn("Authentication is null");
            return null;
        }


// THIS IS GET TOKEN WHEN SOME ONE LOGIN...

        if (authentication instanceof OAuth2AuthenticationToken) {

            // STRICKLY GET THE TOKEN

            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;

            // THEN FIND THE REGISTRATION ID..

            String clientRegistrationId = oauth2Token.getAuthorizedClientRegistrationId();

            // GET PRINCIPLE OBJECT  TO KNOW ABOUT DETAILS..
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


            // THIS STRING HOLD THE EMAIL OR USERNAME...
            String email = null;

            // IF ID EQULS TO GOOGLE ..
            if ("google".equalsIgnoreCase(clientRegistrationId)) {
                logger.info("Getting email from Google login");
                email = oAuth2User.getAttribute("email");
            } // FOR GITHUB..
            else if ("github".equalsIgnoreCase(clientRegistrationId)) {
                logger.info("Getting email from GitHub login");
                email = oAuth2User.getAttribute("email");
                if (email == null) {
                    String login = oAuth2User.getAttribute("login");
                    email = login != null ? login + "@github.com" : null;
                }
            }
          
            // AFTERE GETTING USERNAME IN EMAIL VARIBLE PRINT
            if (email != null) {
                logger.info("OAuth2 user email: {}", email);
                return email;
            } else {
                logger.warn("Could not retrieve email for OAuth2 user");
                return null;
            }
        } /// GETTING USERNAME FROM DATABASE 
         else {
            logger.info("Getting data from local database");
            String username = authentication.getName();
            logger.info("Local user: {}", username);
            return username;
        }
    }


    // this is for genrated token with link for verification of user at signup..

    public static String getLinkForEmailVerification(String emailToken){

        String link= "http://localhost:8081/auth/verify-email?token="+emailToken;

        return link;
    }


}
