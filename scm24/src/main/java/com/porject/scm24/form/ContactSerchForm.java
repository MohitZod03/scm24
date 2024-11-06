package com.porject.scm24.form;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
                                    // this is for search the contact form and get data from the backend to frontend- oblject/field/controller
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactSerchForm {

  private String field;
  private String keyword;

}
