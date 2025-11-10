package com.example.demo9.dto;

import com.example.demo9.constant.Role;
import com.example.demo9.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
  private Long id;

  @NotEmpty(message = "이름은 필수입력입니다.")
  @Length(min = 1, max = 20, message = "1~20자 이하로 입력해 주세요.")
  private String name;

  @NotEmpty(message = "메일은 필수입력입니다.")
  @Email(message = "이메일 형식으로 입력해 주세요.")
  private String email;

  @NotEmpty(message = "비밀번호는 필수입력입니다.")
  @Length(min = 4, max = 20, message = "4~20자 이하로 입력해 주세요.")
  private String password;

  private String address;

  private Role role;


  public static MemberDto entityToDto(Optional<Member> opMember) {
    return MemberDto.builder()
            .id(opMember.get().getId())
            .name(opMember.get().getName())
            .email(opMember.get().getEmail())
            .password(opMember.get().getPassword())
            .address(opMember.get().getAddress())
            .role(opMember.get().getRole())
            .build();
  }
}
