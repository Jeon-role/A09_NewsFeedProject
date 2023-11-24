package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.CommonResponseDto;
import com.example.newsfeedproject.dto.ProfileInformationRequestDto;
import com.example.newsfeedproject.dto.ProfilePasswordRequestDto;
import com.example.newsfeedproject.dto.ProfileResponseDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ProfileServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ProfileResponseDto getProfile(HttpServletRequest req) {
        // 현재 로그인한 사용자의 username 가져오기
        String username = jwtUtil.getUsernameFromHeader(req);

        // 사용자 이름으로 레포지토리에서 사용자 정보 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // ProfileResponseDto 생성 및 반환
        return new ProfileResponseDto(user.getUsername(),user.getEmail(), user.getRole());
    }

    @Override
    public ProfileResponseDto updateProfile(ProfileInformationRequestDto profileRequestDto, HttpServletRequest req) {
        // 현재 로그인한 사용자의 username 가져오기
        String username = jwtUtil.getUsernameFromHeader(req);

        // 사용자 ID로 레포지토리에서 사용자 정보 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자 정보 업데이트
        user.setUsername(profileRequestDto.getNewUsername());
        user.setEmail(profileRequestDto.getNewEmail());


        // 업데이트된 사용자를 리포지토리에 저장
        userRepository.save(user);



        // ProfileResponseDto 생성 및 반환
        return new ProfileResponseDto(user.getUsername(), user.getEmail(), user.getRole());
    }

    @Override
    public CommonResponseDto updatePassword(ProfilePasswordRequestDto passwordRequestDto, HttpServletRequest req) {
        // 현재 로그인한 사용자의 username 가져오기
        String username = jwtUtil.getUsernameFromHeader(req);

        // 사용자 이름으로 리포지토리에서 사용자 정보 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 현재 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(passwordRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 업데이트
        user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));

        // 업데이트된 사용자를 레포지토리에 저장
        userRepository.save(user);

        // 성공 상태 반환
        return new CommonResponseDto("비밀번호가 성공적으로 업데이트되었습니다.", 200);
    }
}
