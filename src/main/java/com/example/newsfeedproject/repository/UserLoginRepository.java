package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin,Long> {

    List<UserLogin> findAllById(Long id);
    Optional<UserLogin> findByUser(User user);



    UserLogin findByToken(String token);
}
