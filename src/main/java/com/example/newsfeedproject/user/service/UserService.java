package com.example.newsfeedproject.user.service;

import com.example.newsfeedproject.user.dto.SignupRequestDto;
import com.example.newsfeedproject.user.dto.StatusDto;
import com.example.newsfeedproject.user.entity.User;
import com.example.newsfeedproject.user.entity.UserLogin;
import com.example.newsfeedproject.user.entity.UserRoleEnum;
import com.example.newsfeedproject.user.repository.UserLoginRepository;
import com.example.newsfeedproject.user.repository.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserLoginRepository userLoginRepository;




    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder ,UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userLoginRepository=userLoginRepository;


    }

    public ResponseEntity<StatusDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        //회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }



        //email 중복 확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 Email입니다.");
        }

        //사용자 등록
        User user = new User(username, password, email,role);
        userRepository.save(user);
        return ResponseEntity.ok(new StatusDto("가입성공", HttpStatusCode.valueOf(200).toString()));
    }

    public ResponseEntity<StatusDto> logout(String username){
            User user =userRepository.findByUsername(username).orElseThrow();

            UserLogin userLogout= userLoginRepository.findByUser(user).orElseThrow();
            userLoginRepository.delete(userLogout);

            return ResponseEntity.ok(new StatusDto("로그아웃 성공", HttpStatusCode.valueOf(200).toString()));

    }
}
