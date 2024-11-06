package com.porject.scm24.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.porject.scm24.helper.AppConstain;
import com.porject.scm24.services.ImageService;
@Service
public class ImageServiceImp implements ImageService {

    // getting object of cloudinaryService loggic there class is AppConfig
    
   

   private Cloudinary cloudinary;

// using constructor injection
@Autowired
public ImageServiceImp(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
}

    @Override
    public String uploadeImage(MultipartFile profileImage , String randomFileName) {

        // for reandom file name
      //  String randomFileName = UUID.randomUUID().toString();
        
        
        // implement the image upload logic here
try {
    // read the image data from the multipart file
    byte[] data = new byte[profileImage.getInputStream().available()];
profileImage.getInputStream().read(data);



// upload the image to cloudinary
cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", randomFileName));

return this.geturlFromPublicId(randomFileName);
} catch (Exception e) {
    e.printStackTrace();
    return null;
}



        // return the url of the image
     
    }


    // get the url that genrate own and pass into as response
    @Override
    public String geturlFromPublicId(String publicId) {
       

        return cloudinary.url().transformation(new Transformation<>()
        .width(AppConstain.CONTACT_IMAGE_WIDTH)
        .height(AppConstain.CONTACT_IMAGE_HEIGHT)
         .crop(AppConstain.CONTACT_IMAGE_CROP)).generate(publicId);
        
       
    }

}
