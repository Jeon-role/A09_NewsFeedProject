package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.ProfileRequestDto;
import com.example.newsfeedproject.dto.ProfileResponseDto;
import com.example.newsfeedproject.dto.StatusDto;

public interface ProfileService {
    ProfileResponseDto getProfile(Long id);
    ProfileResponseDto updateProfile(Long id, ProfileRequestDto profileRequestDto);
    StatusDto updatePassword(ProfileRequestDto passwordRequestDto);
}