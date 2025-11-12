package com.example.demo9.controller;

import com.example.demo9.common.PageVO;
import com.example.demo9.common.Pagination;
import com.example.demo9.dto.BoardDto;
import com.example.demo9.entity.Board;
import com.example.demo9.entity.BoardReply;
import com.example.demo9.entity.Member;
import com.example.demo9.repository.BoardRepository;
import com.example.demo9.service.BoardService;
import com.example.demo9.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

  private final BoardService boardService;
  private final MemberService memberService;
  private final Pagination pagination;

  @GetMapping("/boardList")
  public String guestListGet(Model model, PageVO pageVO) {
    pageVO.setSection("board");
    pageVO = pagination.pagination(pageVO);
    //model.addAttribute("boardList", pageVO.getBoardList());
    model.addAttribute("pageVO", pageVO);

    return "board/boardList";
  }

  @GetMapping("/boardInput")
  public String boardInputGet() {
    return "board/boardInput";
  }

  @PostMapping("/boardInput")
  public String boardInputPost(BoardDto dto, HttpServletRequest request,
                               Authentication authentication,
                               Member member) {
    dto.setHostIp(request.getRemoteAddr());
    String email = authentication.getName();
    member = memberService.getMemberEmailCheck(email).get();

    Board board = Board.dtoToEntity(dto, member);
    Board board_ = boardService.setBoardInput(board);

    if(board_ != null) return "redirect:/message/boardInputOk";
    else return "redirect:/message/boardInputNo";
  }

  @GetMapping("/boardContent")
  public String boardContentGet(Model model, Long id, PageVO pageVO, HttpSession session) {
    // 글 조회수 증가처리(중복방지)
    List<String> contentNum = (List<String>) session.getAttribute("sDuplicate");
    if(contentNum == null) contentNum = new ArrayList<>();
    String imsiNum = "board" + id;
    if(!contentNum.contains(imsiNum)) {
      boardService.setBoardReadNumPlus(id);
      contentNum.add(imsiNum);
    }
    session.setAttribute("sDuplicate", contentNum);

    // 이전글/다음글 가져오기
    Board preVO = boardService.getPreNextSearch(id, "pre");
    Board nextVO = boardService.getPreNextSearch(id, "next");
    model.addAttribute("preVO", preVO);
    model.addAttribute("nextVO", nextVO);

    // 원본 글 가져오기
    Board board = boardService.getBoardContent(id);
    model.addAttribute("board", board);
    model.addAttribute("pageVO", pageVO);

    // 현재 게시글의 관련 댓글 가져오기
    List<BoardReply> replyVos = boardService.getBoardReply(id);
    model.addAttribute("replyVos", replyVos);
    model.addAttribute("newLine", System.lineSeparator());

    return "board/boardContent";
  }

  // 댓글 입력
  @ResponseBody
  @PostMapping("/boardReplyInput")
  public Map<String, Object> boardReplyPost(
          @RequestParam("boardId") Long boardId,
          @RequestParam("content") String content,
          Principal principal,
          HttpServletRequest request
  ) {
    String writeName = principal.getName();
    String hostIp = request.getRemoteAddr();

    BoardReply reply = boardService.setBoardReply(boardId, writeName, content, hostIp);

    Map<String, Object> result = new HashMap<>();
    result.put("id", reply.getId());
    result.put("name", reply.getName());
    result.put("content", reply.getContent());
    result.put("wDate", reply.getWDate());

    return result;
  }

  @GetMapping("/boardDelete")
  public String boardDeleteGet(Long id) {
    boardService.setBoardDelete(id);

    return "redirect:/message/boardDeleteOk";
  }
}
