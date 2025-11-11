package com.example.demo9.dto;

import com.example.demo9.entity.Board;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
  private Long id;

  @NotEmpty(message = "이름은 필수입력입니다.")
  @Column(length = 20, nullable = false)
  private String name;

  @NotEmpty(message = "이메일은 필수입력입니다.")
  @Email(message = " 이메일 형식을 확인하세요")
  @Column(unique = true, length = 50)
  private String email;

  @NotEmpty(message = "제목은 필수입력입니다.")
  private String title;

  @NotEmpty(message = "글내용은 필수입력입니다.")
  private String content;

  private String hostIp;

  private String openSw;

  private int readNum;

  private LocalDateTime wDate;

  private int good;

  private String complaint;

  // Entity to DTO
  public static BoardDto entityToDto(Optional<Board> opBoard) {
    return BoardDto.builder()
            .id(opBoard.get().getId())
            .name(opBoard.get().getName())
            .email(opBoard.get().getMember().getEmail())
            .title(opBoard.get().getTitle())
            .content(opBoard.get().getContent())
            .hostIp(opBoard.get().getHostIp())
            .openSw(opBoard.get().getOpenSw())
            .readNum(opBoard.get().getReadNum())
            .wDate(opBoard.get().getWDate())
            .good(opBoard.get().getGood())
            .complaint(opBoard.get().getComplaint())
            .build();
  }
}
