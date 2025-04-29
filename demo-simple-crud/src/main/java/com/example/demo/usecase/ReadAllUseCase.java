package com.example.demo.usecase;

import com.example.demo.controller.dto.BoardDto;
import com.example.demo.controller.dto.BoardDto.ReadBoard;
import com.example.demo.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadAllUseCase {
    Page<ReadBoard> readAll(Pageable pageable);
}
