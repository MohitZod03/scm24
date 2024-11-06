package com.porject.scm24.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.porject.scm24.entety.User;
import com.porject.scm24.helper.Massage;
import com.porject.scm24.helper.MassageEnum;
import com.porject.scm24.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // verify email

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token, HttpSession session) {

      User user = userRepo.findByEmailToken(token).orElse(null);
      
      if (user != null) {
        if (user.getEmailToken().equals(token)) {

            user.setEmailVerified(true);
            user.setEnabled(true);
            userRepo.save(user);
            session.setAttribute("massage", Massage.builder()
            .type(MassageEnum.green)
            .content("emaild is  verified ")
            .build());

            return "success_page";

        }
        return"error_page";
        
      }

      session.setAttribute("massage", Massage.builder()
      .type(MassageEnum.red)
      .content("emaild is not verified ")
      .build());

                return"error_page";

}
}