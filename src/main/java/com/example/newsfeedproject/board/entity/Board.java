package com.example.newsfeedproject.board.entity;

import com.example.newsfeedproject.board.dto.BoardRequestDto;
import com.example.newsfeedproject.Timestamped;
import com.example.newsfeedproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "board")
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    @Column(name = "content", nullable = false, length = 500)
    private String content;

    //관계설정
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Board (BoardRequestDto boardRequestDto){
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
    }

}
