package com.porject.scm24.services;

import java.util.List;
import java.util.Optional;

import com.porject.scm24.entety.User;

public interface UserServices {

  User savUser(User user);
  Optional<User> getUserById(String id);
  Optional<User> updatUser(User user);
  void deleteUser(String id);
  boolean isUserExist(String userId);
  boolean isUserExistByEmail(String email);
  List<User> getAllUsers();

  User getUserByEmail(String email);

}
