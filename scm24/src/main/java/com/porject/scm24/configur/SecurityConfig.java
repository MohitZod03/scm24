package com.porject.scm24.configur;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



import com.porject.scm24.services.impl.SecurityCustomUserDetailService;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean
    // public UserDetailsService userDetailsService(){
    //   
        // UserDetails user1 = User.withUsername("admin1").password("admin1").roles("ADMIN","USER").build();

        // return new InMemoryUserDetailsManager(user1);
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder(){
        // return new BCryptPasswordEncoder();
    // }
     
    


                             /// now authenticate with database  present user ....


// first going to that class then check database using that class objet have email or not 

@Autowired
private SecurityCustomUserDetailService userDetailService;

@Autowired // this is verification handler..
private AuthFailHandler authFailHandler ;



// this is for OAuth2 suceeshandler ...
 @Autowired
 private OauthAuthenticationSuceessfulHandler authenticationSuceessfulHandler;



@Bean
public AuthenticationProvider authenticationProvider(){

   
DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
// get userDatailService ka object 
daoAuthenticationProvider.setUserDetailsService(userDetailService);


// getting the password encoder cha object ...

daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;

    
}

    

@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}    

    ///   use the class and cretae new class that manage all the access f authentication in project....
 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

          // configrution....

          httpSecurity.authorizeHttpRequests(a->{
            // this is mean give permit to /home...or use ,
                                 //or
            // we authenticate start from user url and permit all other  

                   // a.requestMatchers("/home","/signup","/services").permitAll();
                  
                   //if url come like this then authenticate 
             a.requestMatchers("/user/**").authenticated();     
             // permit all 
             a.anyRequest().permitAll();

             // when use class need form loging ...

            

          });
                /// it is for default login form for the authentication....

                      // httpSecurity.formLogin(Customizer.withDefaults());
                
         
         
                      // we according to LOGIN form with there authentication..

          httpSecurity.formLogin(login->{
            // redirect default custome form url  also chenning(.) is possible...
            login.loginPage("/login");
            //when submit the after login data pass to page go to this page
            login.loginProcessingUrl("/authenticate");
           // to forword after loing then 
           login.successForwardUrl("/user/profile");
           // if error comes then transfer to this page...
           login.failureForwardUrl("/login?error=true");
          // get parameter throw login form 
          login.usernameParameter("email");
          login.passwordParameter("password");


          // to handle verification or other authentication..
          login.failureHandler(authFailHandler);
          
          // we have metthod to handdle the failear 
        //  login.failureHandler(new AuthenticationFailureHandler() {

        //     @Override
        //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        //             AuthenticationException exception) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        //     }

            
        //   });

        //   // same for the success handler when form is login...

        //   login.successHandler(new AuthenticationSuccessHandler() {

        //     @Override
        //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        //             Authentication authentication) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
        //     }
            
        //   });

       });


                                          // OAuth Configrution ..
                                          
                                          
      // httpSecurity.oauth2Login(Customizer.withDefaults());

           // for the userform also see then
         
httpSecurity.oauth2Login(oauth->{
    oauth.loginPage("/login");
    // after google login where is go url... using create new class for this ..

    oauth.successHandler(authenticationSuceessfulHandler);
});




                                /// FOR THE LOGOUT...
              // for logout need to desable csrf bydefault is true it imp but doing this for logout..

         httpSecurity.csrf(AbstractHttpConfigurer::disable);
         httpSecurity.logout(logout->{

            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");

         });                       

        return httpSecurity.build();
    }


                       


    

}
