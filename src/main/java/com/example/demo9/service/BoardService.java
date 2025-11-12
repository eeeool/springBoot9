package com.example.demo9.service;

import com.example.demo9.entity.Board;
import com.example.demo9.entity.BoardReply;
import com.example.demo9.repository.BoardReplyRepository;
import com.example.demo9.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final BoardReplyRepository boardReplyRepository;

  public List<Board> getBoardList() {
    return boardRepository.findAll();
  }

  public Board setBoardInput(Board board) {
    return boardRepository.save(board);
  }

  public Board getBoardContent(Long id) {
//    return boardRepository.findById(id).get();
    return boardRepository.findById(id).orElse(null);
  }

  public void setBoardReadNumPlus(Long id) {
    boardRepository.setBoardReadNumPlus(id);
  }

  public Board getPreNextSearch(Long id, String preNext) {
//    Pageable limitOne = PageRequest.of(0, 1);
    if (preNext.equals("pre")) {
      return boardRepository.findPrevious(id);
    }
    else {
      return boardRepository.findNext(id);
    }
  }

  public List<BoardReply> getBoardReply(Long boardId) {
    return boardReplyRepository.findByBoardIdOrderById(boardId);
  }

  @Transactional
  public void setBoardDelete(Long id) {
    if (!boardRepository.existsById(id)) {
      throw new IllegalArgumentException("삭제실패: 존재하지않는 게시글(id: "+id+")");
    }
    boardRepository.deleteById(id);
  }

  public BoardReply setBoardReply(Long boardId, String writeName, String content, String hostIp) {
    Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.(id="+boardId+")"));

    BoardReply boardReply = BoardReply.builder()
            .board(board)
            .name(writeName)
            .content(content)
            .hostIp(hostIp)
            .build();

    boardReplyRepository.save(boardReply);
    return boardReply;
  }
}
