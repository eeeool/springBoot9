package com.example.demo9.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {
  @GetMapping("/")
  public String homeGet() {
    return "home";
  }

  @PostMapping("/ckeditor/imageUpload")
  @ResponseBody
  public void imageUploadPost(@RequestParam("upload") MultipartFile upload,
                              @RequestParam(value = "CKEditorFuncNum", required = false) String callback,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

    response.setCharacterEncoding("utf-8");

    // 저장 경로 (실제 경로에 맞게 조정)
    String realPathPath = request.getServletContext().getRealPath("/ckeditorUpload/");
    File folder = new File(realPathPath);
    if (!folder.exists()) folder.mkdirs();

    // 파일 저장
    String originalName = upload.getOriginalFilename();
    String newName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + originalName;
    File file = new File(realPathPath, newName);
    upload.transferTo(file);

    String fileUrl = request.getContextPath() + "/ckeditorUpload/" + newName;

    // 콜백 응답
    PrintWriter out = response.getWriter();
    if (callback != null) {
      response.setContentType("text/html;charset=utf-8");
      out.println("<script type='text/javascript'>");
      out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ", '" + fileUrl + "', '이미지 업로드 완료');");
      out.println("</script>");
    } else {
      response.setContentType("application/json;charset=utf-8");
      out.println("{");
      out.println("  \"uploaded\": true,");
      out.println("  \"url\": \"" + fileUrl + "\"");
      out.println("}");
    }
    out.flush();
  }
}
