package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.BoardRequestDto;
import com.example.newsfeedproject.dto.BoardResponseDto;
import com.example.newsfeedproject.dto.CommonResponseDto;
import com.example.newsfeedproject.entity.Board;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.BoardRepository;
import com.example.newsfeedproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto, String username) {
        // 로그인사용자와 게시글 작성자가 같은지 검증
        Board board = checkLoginUserAndPostUser(boardId, username);

        // Update
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }
    // 게시글 삭제 서비스
    public Board deleteBoard(Long boardId, String username) {
        // 로그인사용자와 게시글 작성자가 같은지 검증
        Board board = checkLoginUserAndPostUser(boardId, username);

        // Delelte
        boardRepository.deleteById(boardId);

        return board;
    }

    // 로그인한 사용자와 게시글작성자 대조
    public Board checkLoginUserAndPostUser(Long id, String username){
        // ID로 할일카드 DB조회
        Board board = boardRepository.findById(id).orElseThrow(() ->  new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 로그인한 사용자의 username 추출 및 해당 사용자가 작성한 할일카드 조회
        boardRepository.findALLByUser_Username(username).orElseThrow(() ->  new IllegalArgumentException("로그인한 사용자가 작성한 게시글이 없습니다."));

        // 게시글 작성자와 로그인한 작성자가 일치하는지 검증
        if (!username.equals(board.getUser().getUsername())){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        return board;
    }



}
