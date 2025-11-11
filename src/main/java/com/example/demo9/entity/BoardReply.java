package com.example.demo9.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "boardReply1")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class BoardReply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "board_reply_id")
  private Long id;

  // 원본글의 pk를 fk로 설정
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id", referencedColumnName = "board_id")
  private  Board board;

  @Column(length = 20, nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email", referencedColumnName = "email")
  private Member member;

  @Lob
  @Column(nullable = false)
  private String content;

  @Column(length = 50, nullable = false)
  private String hostIp;

  @CreatedDate
  private LocalDateTime wDate;
}
