package com.example.demo9.dto;

import com.example.demo9.entity.Guest;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDto {
  private Long id;
  private String name;
  private String content;
  private String email;
  private String homePage;
  private LocalDateTime visitDate;
  private String hostIp;

  public static GuestDto entityToDto(Optional<Guest> opGuest) {
    return GuestDto.builder()
            .id(opGuest.get().getId())
            .name(opGuest.get().getName())
            .content(opGuest.get().getContent())
            .email(opGuest.get().getEmail())
            .homePage(opGuest.get().getHomePage())
            .visitDate(opGuest.get().getVisitDate())
            .hostIp(opGuest.get().getHostIp())
            .build();
  }
}
