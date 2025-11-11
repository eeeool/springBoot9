package com.example.demo9.entity;

import com.example.demo9.dto.GuestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Entity
@Table(name = "guest1")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Guest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "guest_id")
  private Long id;

  @Column(length = 20, nullable = false)
  private String name;

  @Lob
  @Column(nullable = false)
  private String content;

  @Email
  @Column(length = 50, nullable = true)
  private String email;

  @URL
  @Column(length = 50, nullable = true)
  private String homePage;

  @CreatedDate
  private LocalDateTime visitDate;

  @Column(length = 20, nullable = false)
  private String hostIp;

  public static Guest dtoToEntity(GuestDto dto) {
    return Guest.builder()
            .name(dto.getName())
            .content(dto.getContent())
            .email(dto.getEmail())
            .homePage(dto.getHomePage())
            .visitDate(dto.getVisitDate())
            .hostIp(dto.getHostIp())
            .build();
  }
}
