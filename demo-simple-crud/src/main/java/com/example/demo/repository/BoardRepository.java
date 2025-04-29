package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
//    List<Board> readBoardById(Long id);
}
