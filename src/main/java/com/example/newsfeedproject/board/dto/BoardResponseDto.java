package com.example.newsfeedproject.board.dto;

import com.example.newsfeedproject.board.entity.Board;
import com.example.newsfeedproject.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BoardResponseDto extends CommonResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = new UserDto(board.getUser());
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
