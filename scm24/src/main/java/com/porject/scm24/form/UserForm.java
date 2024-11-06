package com.porject.scm24.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "username  is Required")
    @Size(min = 3, message = "min 3 charecters is required")
    private String name;
    @NotBlank(message = "massage is required...")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "password is requered..")
    @Size(min = 6, message = "min 6 charecter requered..")
    private String password;
    @NotBlank(message = "about is requred")
    private String about;
    @Size(min = 8,max = 12, message = "Invalid phone no..")
    private String phoneNumber;

}
