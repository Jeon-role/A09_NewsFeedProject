package com.example.newsfeedproject.user.repository;

import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin,Long> {


    Optional<UserLogin> findByUser(User user);



    UserLogin findByToken(String token);
}
