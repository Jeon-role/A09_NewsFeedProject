package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.ProfileRequestDto;
import com.example.newsfeedproject.dto.ProfileResponseDto;
import com.example.newsfeedproject.dto.StatusDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ProfileResponseDto getProfile(Long id) {
        // 사용자 ID로 레포지토리에서 사용자 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // ProfileResponseDto 생성 및 반환
        return new ProfileResponseDto(user.getUsername());
    }

    @Override
    public ProfileResponseDto updateProfile(Long id, ProfileRequestDto profileRequestDto) {
        // 사용자 ID로 레포지토리에서 사용자 정보 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자 정보 업데이트
        user.setUsername(profileRequestDto.getUsername());

        // 업데이트된 사용자를 리포지토리에 저장
        userRepository.save(user);

        // ProfileResponseDto 생성 및 반환
        return new ProfileResponseDto(user.getUsername());
    }

    @Override
    public StatusDto updatePassword(ProfileRequestDto passwordRequestDto) {
        // 사용자 이름으로 리포지토리에서 사용자 정보 조회
        User user = userRepository.findByUsername(passwordRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 현재 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(passwordRequestDto.getPassword(), user.getPassword())) {
            return new StatusDto("현재 비밀번호가 일치하지 않습니다.", "400");
        }

        // 비밀번호 업데이트
        user.setPassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));

        // 업데이트된 사용자를 레포지토리에 저장
        userRepository.save(user);

        // 성공 상태 반환
        return new StatusDto("비밀번호가 성공적으로 업데이트되었습니다.", "200");
    }
}
