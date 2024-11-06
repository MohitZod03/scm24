package com.porject.scm24.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

public static void removMassage(){
    try{

        System.out.println("removing session from the session...");

        HttpSession session= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
          session.removeAttribute("massage");
    }catch(Exception e){
        System.out.println("error in massage remove "+e);
        e.printStackTrace();
    }
}




}
