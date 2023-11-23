package com.example.newsfeedproject.scheduler;

import com.example.newsfeedproject.entity.UserLogout;
import com.example.newsfeedproject.repository.UserLogoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final UserLogoutRepository userLogoutRepository;




    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0/5 * * * * ")
    public void logoutTokencheck(){
        //이거는 스케줄러로
        log.info("스케줄러 실행");

        List<UserLogout> userLogoutList = userLogoutRepository.findAll();
        for(UserLogout userLogout: userLogoutList){
            System.out.println("userLogout.getId() = " + userLogout.getId());
            LocalDateTime now= userLogout.getCreatedAt().plusHours(1);
            LocalDateTime realNow = LocalDateTime.now();
            if(realNow.isAfter(now)){
                userLogoutRepository.delete(userLogout);
            }
        }
    }


}
