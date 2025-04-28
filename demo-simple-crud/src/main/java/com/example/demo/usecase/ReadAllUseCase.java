package com.example.demo.usecase;

import com.example.demo.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadAllUseCase {
    Page<Board> readAll(Pageable pageable);
}
