package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.LoginRequestDto;
import com.example.newsfeedproject.dto.SignupRequestDto;
import com.example.newsfeedproject.dto.StatusDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserLogout;
import com.example.newsfeedproject.entity.UserRoleEnum;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.repository.UserLogoutRepository;
import com.example.newsfeedproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserLogoutRepository userLogoutRepository;

    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder ,JwtUtil jwtUtil,UserLogoutRepository userLogoutRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil=jwtUtil;
        this.userLogoutRepository=userLogoutRepository;
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

    public ResponseEntity<StatusDto> login(LoginRequestDto loginRequestDto, HttpServletResponse res){
        String username=loginRequestDto.getUsername();
        String password=loginRequestDto.getPassword();


        
        User user= userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없어요"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            return ResponseEntity.ok(new StatusDto("비밀번호가 일치하지 않습니다.", HttpStatusCode.valueOf(400).toString()));
        }

        String token= jwtUtil.createToken(user.getUsername(),user.getRole());
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER,token);
        UserLogout userLogout= new UserLogout();
        userLogout.setUser(user);
        userLogoutRepository.save(userLogout);


        return ResponseEntity.ok(new StatusDto("로그인성공", HttpStatusCode.valueOf(200).toString()));
    }

    public ResponseEntity<StatusDto> logout(HttpServletRequest request) {
        String tokenValue = jwtUtil.getJwtFromHeader(request);



        Claims claims;
        if(tokenValue != null) {
            if (jwtUtil.validateToken(tokenValue)) {
                claims = jwtUtil.getUserInfoFromToken(tokenValue);
            } else {
                throw new IllegalArgumentException("token Error");
            }
            User user =userRepository.findByUsername(claims.getSubject()).orElseThrow(NullPointerException::new);

            UserLogout userLogout= userLogoutRepository.findByUser(user);
            userLogoutRepository.delete(userLogout);

            return ResponseEntity.ok(new StatusDto("로그아웃 성공", HttpStatusCode.valueOf(200).toString()));
        }
        else {
            return null;
        }
    }
}
