package com.porject.scm24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.porject.scm24.entety.User;
import com.porject.scm24.form.UserForm;
import com.porject.scm24.helper.Massage;
import com.porject.scm24.helper.MassageEnum;
import com.porject.scm24.services.impl.UserServiceImple;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class PageController {



    @Autowired

    private UserServiceImple userServiceImple;

    @RequestMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getPage(Model model) {
        model.addAttribute("name", "MOHIT WEb site");
        model.addAttribute("gitup", "https://github.com/MohitZod03");
        System.out.println("it is project test ok");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("check", true);
        System.out.println("about page loaded ");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        return "services";
    }

    @RequestMapping("/contact")
    public String getContact() {
        return "contact";
    }

    @RequestMapping("/login")
    public String getLogin() {
        return "login";
    }

    @RequestMapping("/signup")
    public String getSignup(Model model) {
        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);

        return "register";
    }

    // feature show page here ...
    @RequestMapping("/feature")
    public String getfeature() {
        return "feature";
    }

// support me page...

@RequestMapping("/help")
public String getSupport() {
    return "help";
}

@PostMapping("/issue")
public String putIssue(){
    return"help";
}




    // form processing here....

    @PostMapping("/do-register")
    public String formProces(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,HttpSession session) {
        // feach data from user form

        System.out.println("registation processing...");

        System.out.println(userForm);

        // validate from data
        if (bindingResult.hasErrors()) {
            return "register";
        }

        // todo

        // services....

        // to save the user need to build first user // problem is not get default value
        // so like- provider-self.

        // User user =
        // User.builder().name(userForm.getName()).email(userForm.getEmail()).password(userForm.getPassword()).about(userForm.getAbout()).phoneNumber(userForm.getPhoneNumber()).profilePic("https://www.vecteezy.com/vector-art/20765399-default-profile-account-unknown-icon-black-silhouette").build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());

        // this is for verfication default set not verifiend email/user..
        user.setEnabled(false);
        user.setProfilePic(
                "https://www.vecteezy.com/vector-art/20765399-default-profile-account-unknown-icon-black-silhouette");
        // save to database...
        User user2 = userServiceImple.savUser(user);

        System.out.println("it is going :" + user2);

        // massage alert box using the HttpSession object.. or dyanmic pass using
        // helper/massage class

        Massage massage = Massage.builder().content("Registration successful").type(MassageEnum.blue).build();

        session.setAttribute("massage", massage);

        return "redirect:/signup";
    }
}
