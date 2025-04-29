package com.example.demo.usecase;

import com.example.demo.controller.dto.BoardDto.ReadBoard;

public interface ReadOneUseCase {
    ReadBoard readOne(Long id);
}
