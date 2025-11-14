package com.example.demo9.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

  @GetMapping("/upload/uploadForm")
  public String uploadFormGet() {
    return "study/upload/uploadForm";
  }

  @PostMapping("/upload/uploadForm")
  public String uploadFormPost(MultipartFile sFile, HttpServletRequest request) {
    String oFileName = sFile.getOriginalFilename();

    if (oFileName.equals("")) return "redirect:/message/uploadEmpty";

    String sFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + oFileName;

    // 서버에 파일이 저장될 경로 설정
    String realPath = request.getServletContext().getRealPath("/work/uploadTest/");

    try {
      writeFile(sFile, sFileName, realPath);
      return "redirect:/message/fileUploadOk";
    } catch (IOException e) {
      e.printStackTrace();
      return "redirect:/message/fileUploadNo";
    }
  }

  private void writeFile(MultipartFile sFile, String sFileName, String realPath) throws IOException {
    FileOutputStream fos = new FileOutputStream(realPath + sFileName);

    if (sFile.getBytes().length != -1) {
      fos.write(sFile.getBytes());
    }
    fos.flush();
    fos.close();
  }

  @PostMapping("/upload/multiUpload")
  public String multiUploadPost(MultipartHttpServletRequest mFiles, HttpServletRequest request) {
    List<MultipartFile> fileList = mFiles.getFiles("mFile");

    try {
      for(MultipartFile file : fileList) {
        String oFileName = file.getOriginalFilename();
        String sFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + oFileName;
        String realPath = request.getServletContext().getRealPath("/work/uploadTest/");
        writeFile(file, sFileName, realPath);
      }
      return "redirect:/message/fileUploadOk";
    } catch (IOException e) {
      e.printStackTrace();
      return "redirect:/message/fileUploadNo";
    }
  }

  // 서버에 저장된 파일 리스트 가져오기
  @GetMapping("/upload/uploadList")
  public String uploadListGet(Model model, HttpServletRequest request) {
    model.addAttribute("userCsrf", true);

    String realPath = request.getServletContext().getRealPath("/work/uploadTest/");

    String[] files = new File(realPath).list();
    model.addAttribute("files", files);
    model.addAttribute("fileCnt", files.length);

    return "study/upload/uploadList";
  }

  // 파일 삭제처리(선택파일)
  @ResponseBody
  @PostMapping("/upload/fileSelectDelete")
  public String fileSelectDeleteGet(HttpServletRequest request, String delItems) {
    String res = "0";
    String realPath = request.getServletContext().getRealPath("/work/uploadTest/");
    delItems = delItems.substring(0, delItems.length()-1);

    String[] fileNames = delItems.split("/");

    for(String fileName : fileNames) {
      String realPathFile = realPath + fileName;
      new File(realPathFile).delete();
      res = "1";
    }
    return res;
  }
}
