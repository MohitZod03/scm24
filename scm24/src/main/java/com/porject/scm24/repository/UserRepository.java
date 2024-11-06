package com.porject.scm24.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.porject.scm24.entety.User;

@Repository
public interface UserRepository extends JpaRepository<User, String > {

Optional<User> findByEmail(String email);
Optional<User> findByEmailAndPassword(String email, String password);

// it is help to find token sed on email
Optional<User> findByEmailToken(String emailToken);

}
    