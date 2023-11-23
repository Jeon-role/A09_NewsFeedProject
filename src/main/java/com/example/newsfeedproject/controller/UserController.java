package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.LoginRequestDto;
import com.example.newsfeedproject.dto.SignupRequestDto;
import com.example.newsfeedproject.dto.StatusDto;
import com.example.newsfeedproject.repository.UserRepository;
import com.example.newsfeedproject.service.UserService;
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

//    @GetMapping("/user/signup")
//    public void signupPage() {
//        return; //리턴 페이지 수정해야함!!! 회원가입 페이지 반환하기
//    }

    @PostMapping("/user/signup")
    public ResponseEntity<StatusDto> signup(@RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

//    @PostMapping("/user/login")
//    public ResponseEntity<StatusDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res){
//        return userService.login(loginRequestDto,res);
//    }


}
