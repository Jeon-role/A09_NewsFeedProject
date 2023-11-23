package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.ProfileRequestDto;
import com.example.newsfeedproject.dto.ProfileResponseDto;
import com.example.newsfeedproject.dto.StatusDto;
import com.example.newsfeedproject.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // 사용자 ID로 프로필 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long id) {
        ProfileResponseDto profileResponseDto = profileService.getProfile(id);
        return ResponseEntity.ok(profileResponseDto);
    }

    // 사용자 ID로 프로필 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDto profileRequestDto) {
        ProfileResponseDto updatedProfile = profileService.updateProfile(id, profileRequestDto);
        return ResponseEntity.ok(updatedProfile);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<StatusDto> updatePassword(@RequestBody ProfileRequestDto passwordRequestDto) {
        StatusDto statusDto = profileService.updatePassword(passwordRequestDto);
        return ResponseEntity.ok(statusDto);
    }
}