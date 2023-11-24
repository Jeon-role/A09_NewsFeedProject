package com.example.newsfeedproject.scheduler;

import com.example.newsfeedproject.entity.UserLogin;
import com.example.newsfeedproject.repository.UserLoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final UserLoginRepository userLogoutRepository;




    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0/5 * * * * ")
    public void logoutTokencheck(){
        log.info("스케줄러 실행");
        List<UserLogin> userLoginList = userLogoutRepository.findAll();
        if(!userLoginList.isEmpty()){
            for(UserLogin userLogin: userLoginList){
                System.out.println("userLogout.getId() = " + userLogin.getId());
                LocalDateTime now= userLogin.getCreatedAt().plusHours(1);
                LocalDateTime realNow = LocalDateTime.now();
                if(realNow.isAfter(now)){
                    userLogoutRepository.delete(userLogin);
                }
            }
        }


    }


}
