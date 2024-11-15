package com.porject.scm24.configur;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.porject.scm24.entety.Providers;
import com.porject.scm24.entety.User;
import com.porject.scm24.helper.AppConstain;
import com.porject.scm24.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// this class purpose redirect after the google authentication...

@Configuration
public class OauthAuthenticationSuceessfulHandler implements AuthenticationSuccessHandler {


    /// this is need for the save on database ...
    @Autowired
    private UserRepository repository;


 Logger logger = LoggerFactory.getLogger(OauthAuthenticationSuceessfulHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

                //FIRST IDENTIFY  WHICH OAUTH2 PROVIDER...

// crate variable which hold token..

var oauth2AuthenticationClindToken =(OAuth2AuthenticationToken) authentication;
 
String authorizeclientRegistratonId  =oauth2AuthenticationClindToken.getAuthorizedClientRegistrationId();

logger.info(authorizeclientRegistratonId);

var ouatUser = (DefaultOAuth2User)authentication.getPrincipal();


// get the user object to store the authenticater detail in db..

User user = new User();
user.setUserId(UUID.randomUUID().toString());
user.setRoleList(List.of(AppConstain.ROLE_USER));
user.setEmailVerified(true);
user.setEnabled(true);
user.setPassword("dummyPassword");
user.setAbout("this is account create with google");
if (authorizeclientRegistratonId.equalsIgnoreCase("google")) {
    
//google login
user.setEmail(ouatUser.getAttribute("email").toString());

user.setProfilePic(ouatUser.getAttribute("picture").toString());
user.setName(ouatUser.getAttribute("name").toString());

user.setProviderUserId(ouatUser.getName());
user.setProvider(Providers.GOOGLE);
user.setAbout("This account was created with Google");


}
else if (authorizeclientRegistratonId.equalsIgnoreCase("github")) {
    //github login
    String email = ouatUser.getAttribute("email") != null ? 
                   ouatUser.getAttribute("email").toString() : 
                   ouatUser.getAttribute("login").toString() + "@github.com";

    String picture = ouatUser.getAttribute("avatar_url") != null ? 
                     ouatUser.getAttribute("avatar_url").toString() : 
                     null;

    String name = ouatUser.getAttribute("name") != null ? 
                  ouatUser.getAttribute("name").toString() : 
                  ouatUser.getAttribute("login").toString();

    String providerUserId = ouatUser.getName();

    user.setEmail(email);
    user.setProfilePic(picture);
    user.setName(name);
    user.setProviderUserId(providerUserId);
    user.setProvider(Providers.GITHUB);
    user.setAbout("this is account create with github");


}
else if (authorizeclientRegistratonId.equalsIgnoreCase("linkedin")) {
    //linkedin login


}else{
    logger.info("OauthAuthenticationSuceessfulHandler: unknow");
}



           /// then the save the user ...
           
           User user3= repository.findByEmail(user.getEmail()).orElse(null);
if (user3==null) {
    repository.save(user);
   
}

                
                

  


                

              
              
                                  //OR///
              
              
                // get information from login with googlee... THIS IS AFFECT ON GITHUB LOGIN

            /*    DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();

                logger.info(user.getName());
                user.getAttributes().forEach((key,value)->{
                    logger.info("{}->{}",key,value);
                });

                logger.info(user.getAuthorities().toString());

                // save the data into database....

         String email= user.getAttribute("email").toString();
         String name= user.getAttribute("name").toString();
         String picture= user.getAttribute("picture").toString();


         // create the user and save into database..

         User user2= new User();
           
         user2.setEmail(email);
         user2.setName(name);
         user2.setProfilePic(picture);
         user2.setPassword("password");
         user2.setUserId(UUID.randomUUID().toString());
         user2.setProvider(Providers.GOOGLE);
         user2.setEnabled(true);
         user2.setEmailVerified(true);
         user2.setProviderUserId(user.getName());
         user2.setRoleList(List.of(AppConstain.ROLE_USER));
         user2.setAbout("this accound created using google...");

         // we need first find the email already exist or not 

    User user3= repository.findByEmail(email).orElse(null);

    if (user3==null) {
        repository.save(user2);
        logger.info("user is save with emailid"+email);
    }

        
        logger.info("OauthAuthenticationSuceessfulHandler");

        */ 
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
