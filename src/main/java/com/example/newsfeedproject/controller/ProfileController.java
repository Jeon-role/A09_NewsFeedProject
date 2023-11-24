package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.CommonResponseDto;
import com.example.newsfeedproject.dto.ProfileInformationRequestDto;
import com.example.newsfeedproject.dto.ProfilePasswordRequestDto;
import com.example.newsfeedproject.dto.ProfileResponseDto;
import com.example.newsfeedproject.entity.UserLogin;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.repository.UserLoginRepository;
import com.example.newsfeedproject.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtUtil jwtUtil;

    private final UserLoginRepository userLoginRepository;

    public ProfileController(ProfileService profileService, JwtUtil jwtUtil, UserLoginRepository userLoginRepository) {
        this.profileService = profileService;
        this.jwtUtil = jwtUtil;
        this.userLoginRepository = userLoginRepository;
    }

    // 사용자 프로필 조회
    @GetMapping("")
    public ResponseEntity<CommonResponseDto> getProfile(HttpServletRequest req) {
        try {
            ProfileResponseDto profileResponseDto = profileService.getProfile(req);
            return ResponseEntity.ok(profileResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 사용자 프로필 수정
    @PutMapping("")
    public ResponseEntity<CommonResponseDto> updateProfile(@RequestBody ProfileInformationRequestDto profileRequestDto, HttpServletRequest req, HttpServletResponse response) {
        try {
            ProfileResponseDto updatedProfile = profileService.updateProfile(profileRequestDto, req);
            // 토큰 업데이트
            String token = jwtUtil.createToken(updatedProfile.getUsername(), updatedProfile.getRole());

            // 로그인중인 토큰 갱신 및 갱신 토큰 반환
            UserLogin userLogin = userLoginRepository.findByToken(jwtUtil.getJwtFromHeader(req));
            userLogin.setToken(token);
            userLoginRepository.save(userLogin);

            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);


            return ResponseEntity.ok(updatedProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<CommonResponseDto> updatePassword(@RequestBody ProfilePasswordRequestDto passwordRequestDto, HttpServletRequest req) {
        try {
            CommonResponseDto statusDto = profileService.updatePassword(passwordRequestDto, req);
            return ResponseEntity.ok(statusDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }
}