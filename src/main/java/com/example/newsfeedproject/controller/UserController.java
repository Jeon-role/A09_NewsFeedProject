package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.SignupRequestDto;
import com.example.newsfeedproject.repository.UserRepository;
import com.example.newsfeedproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/signup")
    public void signupPage() {
        return; //리턴 페이지 수정해야함!!! 회원가입 페이지 반환하기
    }

    @PostMapping("/user/signup")
    public void signup(SignupRequestDto requestDto) {
        userService.signup(requestDto);
        //return ; //로그인 페이지 반환하기!!!
    }
}
