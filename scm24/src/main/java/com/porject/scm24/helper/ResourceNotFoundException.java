package com.porject.scm24.helper;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String massage){
        super(massage);
    }
public ResourceNotFoundException(){
    super("Resource not found");
}



}
