package com.example.demo9.controller;

import com.example.demo9.dto.PageDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

  @RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
  public String getMessage(Model model, HttpSession session, PageDto pageDto,
                           @PathVariable String msgFlag,
                           @RequestParam(name="mid", defaultValue = "", required = false) String mid,
                           @RequestParam(name="idx", defaultValue = "0", required = false) int idx,
                           @RequestParam(name="tempFlag", defaultValue = "", required = false) String tempFlag
                           //@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
                           //@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
  ) {

    if(msgFlag.equals("guestInputOk")) {
      model.addAttribute("message", "방명록에 글이 등록되었습니다.");
      model.addAttribute("url", "/guest/guestList");
    }
    else if(msgFlag.equals("guestDeleteOk")) {
      model.addAttribute("message", "방명록에 게시글이 삭제되었습니다.");
      model.addAttribute("url", "/guest/guestList");
    }
    else if(msgFlag.equals("boardInputOk")) {
      model.addAttribute("message", "게시판에 글이 등록되었습니다.");
      model.addAttribute("url", "/board/boardList");
    }
    else if(msgFlag.equals("boardInputNo")) {
      model.addAttribute("message", "게시판 글 등록 실패");
      model.addAttribute("url", "/board/boardInput");
    }
    return "include/message";
  }
}
