package com.example.demo.usecase;

import com.example.demo.controller.dto.BoardDto.CreateBoard;
import com.example.demo.domain.Board;

public interface CreactUseCase {
    Board create(CreateBoard boardDto);
}
