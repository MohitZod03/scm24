package com.porject.scm24.entety;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;  // Change this import
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;

// for cloudinary

private String cloudinaryImagePublicId;

   
    @ManyToOne 
    @JsonIgnore 
    private User user;

   @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER ,orphanRemoval = true)
   @JsonIgnore
    private List<SocialLink> socialLinks = new ArrayList<>();

}