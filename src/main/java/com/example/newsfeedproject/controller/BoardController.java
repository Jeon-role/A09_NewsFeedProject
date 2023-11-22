package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.BoardRequestDto;
import com.example.newsfeedproject.dto.BoardResponseDto;
import com.example.newsfeedproject.dto.CommonResponseDto;
import com.example.newsfeedproject.jwt.JwtUtil;
import com.example.newsfeedproject.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<BoardResponseDto> postBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest req){
       BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, jwtUtil.getUsernameFromHeader(req));
       return ResponseEntity.status(201).body(boardResponseDto);
    }

    // 게시글 전체 조회
    @GetMapping("")
    public ResponseEntity<List<BoardResponseDto>> getAllBoard(){
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        boardResponseDtoList = boardService.printAllBoard();

        return ResponseEntity.status(200).body(boardResponseDtoList);
    }

    // 게시글 선택 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<CommonResponseDto> getBoard(@PathVariable Long boardId){
        try {
            BoardResponseDto boardResponseDto = new BoardResponseDto(boardService.getBoard(boardId));
            return ResponseEntity.ok().body(boardResponseDto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시글 수정


    // 게시글 삭제





}
