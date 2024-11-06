package com.porject.scm24.services;

public interface EmailServices {

    void sendEmail(String to, String subject , String body);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();

}
