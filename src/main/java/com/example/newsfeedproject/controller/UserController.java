package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.LoginRequestDto;
import com.example.newsfeedproject.dto.SignupRequestDto;
import com.example.newsfeedproject.dto.StatusDto;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.repository.UserRepository;
import com.example.newsfeedproject.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService,JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil=jwtUtil;
    }


    @PostMapping("/user/signup")
    public ResponseEntity<StatusDto> signup(@RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<StatusDto> logout(HttpServletRequest req){
        return userService.logout(jwtUtil.getUsernameFromHeader(req));
    }

}
