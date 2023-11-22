package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.BoardRequestDto;
import com.example.newsfeedproject.dto.BoardResponseDto;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    private final JwtUtil jwtUtil;

    public BoardController(BoardService boardService, JwtUtil jwtUtil) {
        this.boardService = boardService;
        this.jwtUtil = jwtUtil;
    }

    // 게시글 생성
    @PostMapping("")
    public ResponseEntity<BoardResponseDto> postBoard(@RequestBody BoardRequestDto boardRequestDto @AuthenticationPrincipal UserDetailsImpl userDetails){
       BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, userDetails.getUser());
       return ResponseEntity.status(201).body(boardResponseDto);
    }

    // 게시글 전체 조회

    // 게시글 선택 조회

    // 게시글 수정

    // 게시글 삭제





}
