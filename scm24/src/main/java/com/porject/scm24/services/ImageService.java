package com.porject.scm24.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadeImage(MultipartFile profileImage, String randomFileName);

    String geturlFromPublicId(String publicId);

}
