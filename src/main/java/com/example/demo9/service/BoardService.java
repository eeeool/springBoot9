package com.example.demo9.service;

import com.example.demo9.entity.Board;
import com.example.demo9.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;

  public List<Board> getBoardList() {
    return boardRepository.findAll();
  }

  public Board setBoardInput(Board board) {
    return boardRepository.save(board);
  }
}
