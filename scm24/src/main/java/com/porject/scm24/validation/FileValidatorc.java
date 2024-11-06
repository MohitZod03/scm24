package com.porject.scm24.validation;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// this is for custom validation of file of image


public class FileValidatorc implements ConstraintValidator<ValidFile,MultipartFile> {

    // this is fix size of file
    private static final long MAX_FILE_SIZE=1020*1024*2; // THIS IS MEAN SIZE OF FILE IS MAX 2MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
       

// file not empty
if (file==null || file.isEmpty()) {
    // context.disableDefaultConstraintViolation();
    // context.buildConstraintViolationWithTemplate("fill cannot be blank").addConstraintViolation();

    return true;
}
//file size
if (file.getSize()>MAX_FILE_SIZE) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("file size must be less than 2MB").addConstraintViolation();
    return false;
}



       
return true;

    }

}
