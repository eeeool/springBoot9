package com.example.demo9.controller;

import com.example.demo9.dto.MemberDto;
import com.example.demo9.entity.Member;
import com.example.demo9.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String homeGet() {
    return "home";
  }

  @GetMapping("/memberLogin")
  public String memberLoginGet() {
    return "member/memberLogin";
  }

  @GetMapping("/memberLoginOk")
  public String memberLoginOkGet(
          RedirectAttributes rttr,
          HttpServletRequest request,
          HttpServletResponse response,
                                 Authentication authentication,
                                 HttpSession session
  ) {
    String email = authentication.getName();
    System.out.println("로그인한 email : " + email);
//    String name = memberService.getMemberEmailCheck(email).get().getName();
//    String strLevel = memberService.getMemberEmailCheck(email).get().getRole().toString();
    Optional<Member> opMember = memberService.getMemberEmailCheck(email);

    // 등급 정보 처리
    String strLevel = opMember.get().getRole().toString();
    if(strLevel.equals("ADMIN")) strLevel = "관리자";
    else if(strLevel.equals("OPERATOR")) strLevel = "운영자";
    else if(strLevel.equals("USER")) strLevel = "정회원";

    // Http세션에 필요한 정보 저장
    session.setAttribute("sName", opMember.get().getName());
    session.setAttribute("strLevel", strLevel);

    rttr.addFlashAttribute("message", opMember.get().getName() + "님 로그인 되셨습니다.");

    return "redirect:/member/memberMain";
  }

  @GetMapping("/error")
  public String loginErrorGet(RedirectAttributes rttr) {
    rttr.addFlashAttribute("loginErrorMsg", "아이디 또는 비밀번호가 일치하지 않습니다.");
    return "redirect:/member/memberLogin";
  }

  @GetMapping("/memberLogout")
  public String MemberLogoutGet(
          Authentication authentication,
          HttpServletResponse response,
          HttpServletRequest request,
          HttpSession session,
          RedirectAttributes rttr
  ) {
    if (session != null) {
      String name = session.getAttribute("sName").toString();

      rttr.addFlashAttribute("message", name + "님 로그아웃 되었습니다.");
      session.invalidate();
      new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
    return "redirect:/member/memberLogin";
  }

  @GetMapping("/memberJoin")
  public String memberJoinGet(Model model) {
    model.addAttribute("memberDto", new MemberDto());
    return "member/memberJoin";
  }

  @PostMapping("/memberJoin")
  public String memberJoinPost(RedirectAttributes rttr,
                               @Valid MemberDto dto,
                               BindingResult bindingResult) {
    if(bindingResult.hasErrors()) {
      return "member/memberJoin";
    }

    try {
      Member member = Member.dtoToEntity(dto, passwordEncoder);
      Member memberRes = memberService.saveMember(member);
      System.out.println("==> " + memberRes);
      rttr.addFlashAttribute("message", "회원에 가입되었습니다.");
      return "redirect:/member/memberLogin";
    } catch (IllegalStateException e) {
      rttr.addFlashAttribute("message", e.getMessage());
      return "redirect:/member/memberJoin";
    }
  }

  @GetMapping("memberMain")
  public String memberMainGet() {
    return "member/memberMain";
  }
}
