package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.BoardRequestDto;
import com.example.newsfeedproject.dto.BoardResponseDto;
import com.example.newsfeedproject.entity.Board;
import com.example.newsfeedproject.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 생성 서비스
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user){
        // Dto -> Entity
        Board board = new Board(boardRequestDto);

        // 로그인한 사용자
        board.setUser(user);

        // repository 저장
        boardRepository.save(board);

        return BoardResponseDto(board);
    }

    // 게시글 전체 조회 서비스

    // 게시글 선택 조회 서비스

    // 게시글  수정 서비스

    // 게시글 삭제 서비스





}
