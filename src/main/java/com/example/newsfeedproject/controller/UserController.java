package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.LoginRequestDto;
import com.example.newsfeedproject.dto.SignupRequestDto;
import com.example.newsfeedproject.dto.StatusDto;
import com.example.newsfeedproject.repository.UserRepository;
import com.example.newsfeedproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user/signup")
    public ResponseEntity<StatusDto> signup(@RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<StatusDto> logout(HttpServletRequest request){
        return userService.logout(request);
    }

    

}
