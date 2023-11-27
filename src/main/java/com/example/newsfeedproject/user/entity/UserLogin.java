package com.example.newsfeedproject.user.entity;

import com.example.newsfeedproject.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "userslogin")
public class UserLogin extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
