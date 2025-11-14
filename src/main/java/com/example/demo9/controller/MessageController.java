package com.example.demo9.controller;

import com.example.demo9.dto.PageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

  @RequestMapping(value = "/message/{msgFlag}", method = RequestMethod.GET)
  public String getMessage(Model model, HttpSession session,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Authentication authentication,
                           @PathVariable String msgFlag,
                           @RequestParam(name="name", defaultValue = "", required = false) String name,
                           @RequestParam(name="email", defaultValue = "", required = false) String email,
                           @RequestParam(name="id", defaultValue = "0", required = false) Long id,
                           @RequestParam(name="tempFlag", defaultValue = "", required = false) String tempFlag,
                           @RequestParam(name="pag", defaultValue = "0", required = false) int pag
                           //@RequestParam(name="pageSize", defaultValue = "10", required = false) int pageSize
  ) {
    if(msgFlag.equals("memberJoinOk")) {
      model.addAttribute("message", "회원가입 되었습니다.");
      model.addAttribute("url", "/member/memberLoginOk");
    }
    else if(msgFlag.equals("memberJoinNo")) {
      model.addAttribute("message", "회원가입에 실패하였습니다.");
      model.addAttribute("url", "/member/memberLoginNo");
    }
    else if(msgFlag.equals("guestInputOk")) {
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
      model.addAttribute("message", "게시판에 글 등록 실패~~");
      model.addAttribute("url", "/board/boardInput");
    }
    else if(msgFlag.equals("boardDeleteOk")) {
      model.addAttribute("message", "게시글이 삭제되었습니다.");
      model.addAttribute("url", "/board/boardList");
    }
    else if(msgFlag.equals("boardDeleteNo")) {
      model.addAttribute("message", "현 게시글에 댓글이 존재합니다.\n\n댓글을 먼저 삭제해 주세요.");
      model.addAttribute("url", "/board/boardContent?id="+id+"&pag="+pag);
    }
    else if(msgFlag.equals("memberDeleteOk")) {
      model.addAttribute("message", "회원이 탈퇴되었습니다.");
      model.addAttribute("url", "/");
    }
    else if(msgFlag.equals("memberPwdChangeOk")) {
      model.addAttribute("message", "비밀번호가 변경되었습니다. 다시 로그인해주세요.");
      model.addAttribute("url", "/member/memberLogout");
    }
    else if(msgFlag.equals("memberUpdateOk")) {
      model.addAttribute("message", "회원 정보가 변경되었습니다.");
      model.addAttribute("url", "/member/memberUpdate?email="+email);
    }
    else if(msgFlag.equals("memberLogout")) {
      model.addAttribute("message", name+"님 로그아웃 되었습니다.");
      model.addAttribute("url", "/member/memberLogin");
    }
    else if(msgFlag.equals("memberDelOk")) {
      session.invalidate();
      new SecurityContextLogoutHandler().logout(request, response, authentication);
      model.addAttribute("message", "탈퇴하신 회원입니다. 같은 아이디로 재가입할수 없습니다.");
      model.addAttribute("url", "/member/memberLogin");
    }
    else if(msgFlag.equals("fileUploadOk")) {
      model.addAttribute("message", "파일 업로드 성공");
      model.addAttribute("url", "/study/upload/uploadForm");
    }
    else if(msgFlag.equals("fileUploadNo")) {
      model.addAttribute("message", "파일 업로드 실패");
      model.addAttribute("url", "/study/upload/uploadForm");
    }
    else if(msgFlag.equals("uploadEmpty")) {
      model.addAttribute("message", "파일 업로드할 파일을 선택하세요");
      model.addAttribute("url", "/study/upload/uploadForm");
    }
    return "include/message";
  }

}
