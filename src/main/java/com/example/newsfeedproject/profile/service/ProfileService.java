package com.example.newsfeedproject.profile.service;

import com.example.newsfeedproject.board.dto.CommonResponseDto;
import com.example.newsfeedproject.profile.dto.ProfileInformationRequestDto;
import com.example.newsfeedproject.profile.dto.ProfilePasswordRequestDto;
import com.example.newsfeedproject.profile.dto.ProfileResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface ProfileService {
    ProfileResponseDto getProfile(HttpServletRequest req);
    ProfileResponseDto updateProfile(ProfileInformationRequestDto profileInformationRequestDto, HttpServletRequest req);
    CommonResponseDto updatePassword(ProfilePasswordRequestDto passwordRequestDto, HttpServletRequest req);
}