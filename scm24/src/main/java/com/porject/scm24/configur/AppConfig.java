package com.porject.scm24.configur;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
            
                // this is class for to store the cloudinary configuration and store


@Configuration
public class AppConfig {


// THIS IS ALL COMES FROM APPLICATION.PROPERTIES
@Value("${cloudinary.cloud.name}")
private String cloudName;
@Value("${cloudinary.api.key}")
private String apiKey;
@Value("${cloudinary.api.secret}")
private String apiSecret;

// create cloudinary object
@Bean
public Cloudinary cloudinary(){
    // direct create map here 
return new Cloudinary(
    ObjectUtils.asMap(
        "cloud_name", cloudName,
        "api_key",apiKey ,
        "api_secret",apiSecret


    )
);





   }




}
