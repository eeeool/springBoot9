package com.example.demo9.repository;

import com.example.demo9.entity.BoardReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long>, QuerydslPredicateExecutor<BoardReply> {

  List<BoardReply> findByBoardIdOrderById(Long boardId);

  Optional<BoardReply> findByBoardId(Long id);
}
