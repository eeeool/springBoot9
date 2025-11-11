package com.example.demo9.controller;

import com.example.demo9.dto.BoardDto;
import com.example.demo9.entity.Board;
import com.example.demo9.entity.Member;
import com.example.demo9.service.BoardService;
import com.example.demo9.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
  private final BoardService boardService;
  private final MemberService memberService;

  @GetMapping("/boardList")
  public String guestListGet(
          Model model,
          @RequestParam(name = "pag", defaultValue = "0", required = false) int pag,
          @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
  ) {
    List<Board> boardList = boardService.getBoardList();

    model.addAttribute("boardList", boardList);

    return "board/boardList";
  }

  @GetMapping("/boardInput")
  public String boardInputGet() {
    return "board/boardInput";
  }

  @PostMapping("/boardInput")
  public String boardInputPost(BoardDto dto, HttpServletRequest request,
                               Authentication authentication, Member member) {
    dto.setHostIp(request.getRemoteAddr());
    String email = authentication.getName();
    member = memberService.getMemberEmailCheck(email).get();

    Board board = Board.dtoToEntity(dto, member);
    Board board_ = boardService.setBoardInput(board);

    if (board_ != null) return "redirect:/message/boardInputOk";
    else return "redirect:/message/boardInputNo";
  }
}
