package com.porject.scm24.entety;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class User implements UserDetails {
    @Id
    private String userId;

    @Column(name = "username",nullable = false)
    private String name;

    @Column(unique = true , nullable = false)
    private String email;
    
     @Getter(value = AccessLevel.NONE)
     @JsonIgnore
    private String password;

    @Column(length = 1000)
    private String about;
    
    @Column(length = 1000)
    private String profilePic;
    private String phoneNumber;

    // this is for the verification of user/ email is verifed or not..
    @Getter(value = AccessLevel.NONE)
    private boolean enabled=false;

    
    
    

    private boolean emailVerified =false;
     private boolean phoneVerified =false; 

     /// self , google , linkdin , github , facebook....
@Enumerated(value = EnumType.STRING)
private Providers provider= Providers.SELF;

private String providerUserId;


 @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JsonManagedReference  // Add this to properly handle the bidirectional relationship
  private List<Contact> contacts= new ArrayList<>();







 // override some method to allow for authenticati...


  // create list that store the user roles inside for athentication...

  @ElementCollection(fetch = FetchType.EAGER)// it stored multiple roles of user in single entity..
  private List<String> roleList =new ArrayList<>();

// this is for the teke verification of user using  token after the signup...
  private String emailToken;


@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
  
// for the grantedAuthority doing this ...
//list of roles[USER,ADMIN]
// create SimpleGrantedAuthority[roles{ADMIN,USER}]
  Collection<SimpleGrantedAuthority> roles =  roleList.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());

 return roles;
}

// email is our username for this project...
@Override
public String getUsername() {
 
 return this.email;
}

@Override
public boolean isAccountNonExpired() {
  return true;
}

@Override
public boolean isAccountNonLocked() {
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	return true;
}
@Override
public boolean isEnabled() {
  return this.enabled;
}

// password that is use by user.
@Override
public String getPassword() {
 

  return this.password;
  
}

}
