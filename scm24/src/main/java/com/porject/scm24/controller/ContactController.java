package com.porject.scm24.controller;


import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import com.porject.scm24.entety.Contact;
import com.porject.scm24.entety.User;
import com.porject.scm24.form.ContactForm;
import com.porject.scm24.form.ContactSerchForm;
import com.porject.scm24.helper.AppConstain;
import com.porject.scm24.helper.Helper;
import com.porject.scm24.helper.Massage;
import com.porject.scm24.helper.MassageEnum;
import com.porject.scm24.services.ContactService;
import com.porject.scm24.services.ImageService;
import com.porject.scm24.services.UserServices;
import com.porject.scm24.services.impl.ContactServicesImp;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.var;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    // create object of logger
    Logger logger=org.slf4j.LoggerFactory.getLogger(ContactController.class);

    // object of contactserviceImp
    @Autowired
    private ContactServicesImp contactServicesImp;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserServices userServices;

// create object of imageService
@Autowired
private ImageService imageService;

    // add the contacts: page
// note this is set data on contactForm fild from there 

    @RequestMapping("/add")
    public String addContacts(Model model){
    ContactForm contactForm =new ContactForm();

   // contactForm.setName("mohit zod");
   // contactForm.setFavorite(true);
    // this way we set data into fild // use in form using (data-th-object/field)
    model.addAttribute("contactForm",contactForm);
  
    
        return"user/add_contacts";
    }
//this is get data from contactform fields  that way use post and modelAttributes..

 @PostMapping("/add")
public String saveContacts(@Valid @ModelAttribute("contactForm") ContactForm contactForm,BindingResult bindingResult, Authentication authentication, HttpSession httpSession ){
System.out.println(contactForm);

           /// validate the field of the contactForm..
 // validate from data // @Valid , Bindi
 if (bindingResult.hasErrors()) {
   
   
    // this is if error in then show in console
    bindingResult.getAllErrors().forEach(error -> {
        logger.info(error.toString());
    });


    // if there is any error in the form field
httpSession.setAttribute("massage", Massage.builder().content("pleass corect error first").type(MassageEnum.red).build());

    return "user/add_contacts";
}
 

         /// know saving this data to the database...

         // get username using helper class
         String username = Helper.getEmailOfLoggedUser(authentication);
         // find user from that username 
         User user = userServices.getUserByEmail(username);


// processing contact image here ...
         // note:  enctype="multipart/form-data" this is must for the file upload in form
        
logger.info("file information : {}",contactForm.getProfileImage().getOriginalFilename());


// there get random file name
String randomFileName=UUID.randomUUID().toString();

// to to process file 
String fileUrl= imageService.uploadeImage(contactForm.getProfileImage(),randomFileName);
 

// 1.need to create object of contact and then set value usin form featch data

   Contact contact = new Contact();

   contact.setName(contactForm.getName());
   contact.setEmail(contactForm.getEmail());
   contact.setPhoneNumber(contactForm.getPhoneNumber());
   contact.setAddress(contactForm.getAddress());        
   contact.setDescription(contactForm.getDescription());
  // note : we also need USER which have CONTACTS that way need authentication also..
                  // get username  and get user by email or username that authenticated..

contact.setUser(user);

contact.setWebsiteLink(contactForm.getWebsiteLink());
contact.setLinkedinLink(contactForm.getLinkedinLink());

// save profile image url in contact object
contact.setPicture(fileUrl);
contact.setFavorite(contactForm.isFavorite());

// save all this contact..
contactServicesImp.saveContact(contact);


// now massage after the // using H

httpSession.setAttribute("massage", Massage.builder().content("you are successfully added new contact..").type(MassageEnum.green).build());

return"redirect:/user/contacts/add";
}



// this is to show all contacts on view page

// this is without pagination 

                         //but we can do with pagination all this how much data we want to show on page
                         
@RequestMapping
public String viewContact(
    @RequestParam(value = "page",defaultValue = "0") int page , 
    @RequestParam(value = "size",defaultValue = AppConstain.PAGE_SIZE+"") int size ,
    @RequestParam(value = "sortBy",defaultValue = "name") String sortBy ,
    @RequestParam(value = "direction",defaultValue = "asc") String direction , 
    Model model,Authentication authentication){

    // get username using helper class
    String username = Helper.getEmailOfLoggedUser(authentication);
    // find user from that username 
    User user = userServices.getUserByEmail(username);

    // get all contacts of that user
   // List<Contact> contacts = contactServicesImp.getByUser(user);
   Page<Contact> contacts = contactServicesImp.getByUser(user, page, size, sortBy, direction);

    model.addAttribute("contacts", contacts);

    // send the page size value to the view page
    model.addAttribute("pageSize", AppConstain.PAGE_SIZE);

    // we also send the serch form to the view page
    model.addAttribute("contactSerchForm",new ContactSerchForm());

    return "user/contacts";
}


                     // THIS IS FOR SEARCH THE CONTACT AND GET DATA FROM THE BACKEND..

@RequestMapping("/search")
public String serchContact(
//  @RequestParam("field") String field,
//  @RequestParam("keyword") String keyword,
             // get data from the frontend to backend in object form
@ModelAttribute("contactSerchForm") ContactSerchForm contactSerchForm,
 @RequestParam(value = "size" ,defaultValue = AppConstain.PAGE_SIZE+"")int size,
 @RequestParam(value = "page",defaultValue = "0")int page,
 @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
 @RequestParam(value = "direction", defaultValue = "asc") String direction,
                    Model model, Authentication authentication )
{  
    
    // to see backed value on consle 
 logger.info("field : {} , keyword : {}",contactSerchForm.getField(),contactSerchForm.getKeyword());

       // this is neccessory for only have own contatact then is return 
       var user = userServices.getUserByEmail(Helper.getEmailOfLoggedUser(authentication));



 Page<Contact> contacts= null;
                // 
 if (contactSerchForm.getField().equalsIgnoreCase("name")) {
       
    contacts   = contactService.serchContactByName(contactSerchForm.getKeyword(),size,page,sortBy,direction,user);

 }
 else if(contactSerchForm.getField().equalsIgnoreCase("email")){
    contacts = contactService.serchContactsByEmail(contactSerchForm.getKeyword(),size,page,sortBy,direction,user);
 }
 else if(contactSerchForm.getField().equalsIgnoreCase("phone")){
    contacts = contactService.serchContactsByPhone(contactSerchForm.getKeyword(),size,page,sortBy,direction,user);
 }
       
 logger.info("contacts : {}",contacts);

 // send the contacts to the view page // mean reason is we need to show the search form on the search page fild..
 model.addAttribute("contactSerchForm",contactSerchForm);

 model.addAttribute("contacts", contacts);

 model.addAttribute("pageSize", AppConstain.PAGE_SIZE);


                    return "user/search";
                  
                
                }  





/// this is for the deletd the contacts from database

// @RequestMapping("/delete/{contactId}")
// public String deleteContact(
//         @PathVariable("contactId") String contactId
//         ){

//     contactServicesImp.delete(contactId);
   
//     logger.info("contactId {} deleted", contactId);

//     return "redirect:/user/contacts";


// }



@GetMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId, 
                              RedirectAttributes redirectAttributes) {
        try {
            contactService.delete(contactId);
            redirectAttributes.addFlashAttribute("message", "Contact deleted successfully!");
            return "redirect:/user/contacts";
        } catch (Exception e) {
            logger.error("Error deleting contact: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Failed to delete contact!");
            return "redirect:/user/contacts";
        }
    }




// @RequestMapping("/delete/{contactId}")
//     public String deleteContact(
//             @PathVariable("contactId") String contactId,
//             HttpSession session) {
//         contactService.delete(contactId);
//         logger.info("contactId {} deleted", contactId);

//         session.setAttribute("message",
//                 Massage.builder()
//                         .content("Contact is Deleted successfully !! ")
//                         .type(MassageEnum.green)
//                         .build()

//         );

//         return "redirect:/user/contacts";
//     }

    


// @DeleteMapping("/delete/{contactId}")
// public ResponseEntity<Void> deleteContact(@PathVariable("contactId") String contactId) {
//     contactService.delete(contactId);
//     logger.info("contactId {} deleted", contactId);
//     return ResponseEntity.noContent().build();
// }




// @DeleteMapping("/delete/{contactId}")
// @ResponseBody
// public ResponseEntity<?> deleteContact(@PathVariable("contactId") String contactId) {
//     try {
//         contactService.delete(contactId);
//         return ResponseEntity.ok()
//             .body(Map.of("message", "Contact deleted successfully", "id", contactId));
//     } catch (ResourceNotFoundException e) {
//         return ResponseEntity.status(HttpStatus.SC_NOT_FOUND)
//             .body(Map.of("error", e.getMessage()));
//     } catch (Exception e) {
//         return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
//             .body(Map.of("error", "Failed to delete contact"));
//     }
// }

















/// this for update view page with old data

@RequestMapping("/view/{contactId}")
public String viewContact(@PathVariable("contactId") String contactId, Model model){

    // get contact by id
  var contact = contactService.getById(contactId);
/// create contactForm object and set data into it for the update the contact
ContactForm contactForm = new ContactForm();    

contactForm.setName(contact.getName());
contactForm.setEmail(contact.getEmail());
contactForm.setPhoneNumber(contact.getPhoneNumber());
contactForm.setAddress(contact.getAddress());
contactForm.setFavorite(contact.isFavorite());
contactForm.setDescription(contact.getDescription());
contactForm.setWebsiteLink(contact.getWebsiteLink());
contactForm.setLinkedinLink(contact.getLinkedinLink());
contactForm.setPicture(contact.getPicture());


    model.addAttribute("contactForm", contactForm);

    // pass the contact id to the view page this is for the update the contact
    model.addAttribute("contactId", contactId);


    return "user/update_contact_view";
}




// this is for the update the contact actually

@PostMapping("/update/{contactId}")
public String updateContact( @PathVariable("contactId") String contactId,@Valid @ModelAttribute("contactForm") ContactForm contactForm,BindingResult bindingResult , Model model){

// update the contact

if (bindingResult.hasErrors()) {
    return "user/update_contact_view";
    
}

// getting old contact data
var con = contactService.getById(contactId);

//con.setId(contactId);
con.setName(contactForm.getName());
con.setEmail(contactForm.getEmail());
con.setPhoneNumber(contactForm.getPhoneNumber());
con.setAddress(contactForm.getAddress());
con.setFavorite(contactForm.isFavorite());
con.setDescription(contactForm.getDescription());
con.setWebsiteLink(contactForm.getWebsiteLink());
con.setLinkedinLink(contactForm.getLinkedinLink());
      // image processing

  if (contactForm.getProfileImage() !=null && !contactForm.getProfileImage().isEmpty()) {
    logger.info("file is not null");
    String randomFileName=UUID.randomUUID().toString();
    String fileUrl= imageService.uploadeImage(contactForm.getProfileImage(),randomFileName);
    con.setCloudinaryImagePublicId(randomFileName);
    con.setPicture(fileUrl);

    contactForm.setPicture(fileUrl);

  }  
  else{
    logger.info("file is null");
  }  





var updatedCon= contactService.updateContact(con);

logger.info("updated contact : {}",updatedCon);

model.addAttribute("massage", Massage.builder().content("Contact updated successfully").type(MassageEnum.green).build());

    return "redirect:/user/contacts/view/"+contactId;


}








}




