package com.porject.scm24.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// this is for create custom  validation of file of image
// this is for make this annotation as a document
@Documented
// this is for where we can use this annotation
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.PARAMETER, ElementType.CONSTRUCTOR})
//this is use for when we can use this annotation
@Retention(RetentionPolicy.RUNTIME)
// this is for make this annotation as a constraint
@Constraint(validatedBy = FileValidatorc.class)

public @interface ValidFile {
String message() default "Invalid file";

Class<?>[] groups() default {};

boolean checkEmpty() default true;


Class<? extends Payload>[] payload() default {};




}   
