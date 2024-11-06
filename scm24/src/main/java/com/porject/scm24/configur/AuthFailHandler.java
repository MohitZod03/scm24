package com.porject.scm24.configur;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.porject.scm24.helper.Massage;
import com.porject.scm24.helper.MassageEnum;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@Component // to use in securityConfig
public class AuthFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        

             // to show massage of not verified
          if (exception instanceof DisabledException) {

            // crerate object to show massage
                
            HttpSession session = request.getSession();
            session.setAttribute("massage", Massage.builder().content("User is Disabled | Email with verification link is send")
            .type(MassageEnum.red).build());
 
            response.sendRedirect("/login");
          }else{
            response.sendRedirect("/login?error=true");
          } 

        
    }
// this class main purpose is when authentication faile any resone or specific by is enabled resone then handle...







}
