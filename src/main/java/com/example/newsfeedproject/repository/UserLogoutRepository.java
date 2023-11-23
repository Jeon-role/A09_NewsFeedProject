package com.example.newsfeedproject.repository;


import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserLogout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLogoutRepository extends JpaRepository<UserLogout,Long> {

    List<UserLogout> findAllById(Long id);
    UserLogout findByUser(User user);

}
