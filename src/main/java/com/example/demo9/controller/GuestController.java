package com.example.demo9.controller;

import com.example.demo9.dto.GuestDto;
import com.example.demo9.entity.Guest;
import com.example.demo9.service.GuestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

  private final GuestService guestService;

  // 방명록 전체 리스트(페이징처리)
  @GetMapping("/guestList")
  public String guestListGet(Model model,
                             @RequestParam(name="pag", defaultValue = "0", required = false) int pag,
                             @RequestParam(name="pageSize", defaultValue = "3", required = false) int pageSize
  ) {

    Page<Guest> dtos = guestService.getGuestList(pag, pageSize);

    int totPage = dtos.getTotalPages();
    int curScrStartNo = (int) dtos.getTotalElements() - (pag * pageSize);

    int blockSize = 3;
    int curBlock = (pag - 1) / blockSize;
    int lastBlock = (totPage - 1) / blockSize;

    model.addAttribute("dtos", dtos);
    model.addAttribute("pag", pag+1);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("totPage", totPage);
    model.addAttribute("curScrStartNo", curScrStartNo);
    model.addAttribute("blockSize", blockSize);
    model.addAttribute("curBlock", curBlock);
    model.addAttribute("lastBlock", lastBlock);

    // 개행처리를 위한 코드
    model.addAttribute("newLine", System.lineSeparator());

    return "guest/guestList";
  }
  // 방명록 등록폼 보기
  @GetMapping("/guestInput")
  public String guestInputGet() {
    return "guest/guestInput";
  }

  // 방명록 등록 처리
  @PostMapping("/guestInput")
  public String guestInputPost(Model model, GuestDto dto, HttpServletRequest request) {
    dto.setHostIp(request.getRemoteAddr());
    Guest guest = Guest.dtoToEntity(dto);
    guestService.setGuestInput(guest);

    return "redirect:/message/guestInputOk";
  }

	/*
	// 관리자 인증폼 보기
	@GetMapping("/admin")
	public String adminGet() {
		return "guest/admin";
	}

	// 관리자 인증처리
	@PostMapping("/admin")
	public String adminPost(String mid, String pwd, HttpSession session) {
		if(mid.equals("admin") && pwd.equals("1234")) {
			session.setAttribute("sAdmin", "adminOK");
			return "redirect:/message/adminOk";
		}
		else return "redirect:/message/adminNo";
	}

	// 관리자 인증 로그아웃
	@GetMapping("/adminOut")
	public String adminOutGet(HttpSession session) {
		session.removeAttribute("sAdmin");

		return "redirect:/message/adminOut";
	}
  */

  // 방명록 게시글 삭제처리
  @GetMapping("/guestDelete")
  public String guestDeleteGet(Long id) {
    guestService.setGuestDelete(id);

    return "redirect:/message/guestDeleteOk";
  }

}
