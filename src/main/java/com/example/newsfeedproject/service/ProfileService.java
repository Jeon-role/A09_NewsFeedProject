package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.CommonResponseDto;
import com.example.newsfeedproject.dto.ProfileInformationRequestDto;
import com.example.newsfeedproject.dto.ProfilePasswordRequestDto;
import com.example.newsfeedproject.dto.ProfileResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ProfileService {
    ProfileResponseDto getProfile(HttpServletRequest req);
    ProfileResponseDto updateProfile(ProfileInformationRequestDto profileInformationRequestDto, HttpServletRequest req);
    CommonResponseDto updatePassword(ProfilePasswordRequestDto passwordRequestDto, HttpServletRequest req);
}