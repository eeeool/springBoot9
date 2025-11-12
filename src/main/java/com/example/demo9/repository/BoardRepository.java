package com.example.demo9.repository;

import com.example.demo9.entity.Board;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

  Page<Board> findByTitleContaining(String searchString, PageRequest pageable);

  Page<Board> findByNameContaining(String searchString, PageRequest pageable);

  Page<Board> findByContentContaining(String searchString, PageRequest pageable);

  @Transactional
  @Modifying
  @Query("update Board b set b.readNum = b.readNum + 1 where b.id = :id")
  void setBoardReadNumPlus(Long id);

  @Query("select b from Board b where b.id < :id order by b.id desc limit 1")
  Board findPrevious(Long id);

  @Query("select b from Board b where b.id > :id order by b.id asc limit 1")
  Board findNext(Long id);
}
