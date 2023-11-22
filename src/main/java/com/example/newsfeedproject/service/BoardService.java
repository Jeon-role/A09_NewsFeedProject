package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.BoardRequestDto;
import com.example.newsfeedproject.dto.BoardResponseDto;
import com.example.newsfeedproject.entity.Board;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.BoardRepository;
import com.example.newsfeedproject.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // 게시글 생성 서비스
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, String username){
        // Dto -> Entity
        Board board = new Board(boardRequestDto);

        // 로그인한 사용자
        User user =  userRepository.findByUsername(username).orElseThrow();
        board.setUser(user);

        // repository 저장
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    // 게시글 전체 조회 서비스
    public List<BoardResponseDto> printAllBoard() {

        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();

        // Entity List 조회
        List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC,"modifiedAt"));

        // Entity List -> Dto List
        boardList.forEach(board -> {
                var boardDto = new BoardResponseDto(board);
                boardResponseDtoList.add(boardDto);
        });

        return boardResponseDtoList;
    }

    // 게시글 선택 조회 서비스
    public Board getBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() ->  new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    // 게시글  수정 서비스

    // 게시글 삭제 서비스





}
