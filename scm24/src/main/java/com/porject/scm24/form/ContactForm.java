package com.porject.scm24.form;

import org.springframework.web.multipart.MultipartFile;

import com.porject.scm24.validation.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
    @NotBlank(message = "ContactName  is Required")
    @Size(min = 3, message = "min 2 charecters is required")
    private String name;
    @NotBlank(message="email is required...")
    @Email(message = "Invalid email address")
    private String email; 
    @NotBlank(message="Phone Number is required...")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    @NotBlank(message="Address  is Required")
    private String address;
    @NotBlank(message="Description is Required")
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;

    // here create the custom validation for the image size and resolution
    // this is come from the validfile annotation
    @ValidFile // this use in field of form
    private MultipartFile profileImage;

   

    private String picture;

}
