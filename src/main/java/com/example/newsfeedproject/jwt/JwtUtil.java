package com.example.newsfeedproject.jwt;

import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.entity.UserLogin;
import com.example.newsfeedproject.entity.UserRoleEnum;
import com.example.newsfeedproject.repository.UserLoginRepository;
import com.example.newsfeedproject.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    private final UserRepository userRepository;

    private final UserLoginRepository userLoginRepository;

    public JwtUtil(UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }


    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }



    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    // JWT SubString
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        try {

            userLoginCheck(token);


            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 헤더에서 사용자 이름 가져오기
    public String getUsernameFromHeader(HttpServletRequest request) {
        String tokenValue = getJwtFromHeader(request);
        tokenValue = substringToken(tokenValue);
        Claims info = getUserInfoFromToken(tokenValue);
        return info.getSubject();
    }

    public void userLoginCheck(String token){
        Claims claims = getUserInfoFromToken(token);
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException(" 유저가 존재하지않아요"));
        UserLogin userLogin = userLoginRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("유저로그인에 존재하지않아요"));

        String token1 = substringToken(userLogin.getToken());

        if(!token1.equals(token)){
            throw new IllegalArgumentException("토큰이 맞지않아요");
        }

    }

}
