package com.example.demo.usecase;

import com.example.demo.controller.dto.BoardDto;
import com.example.demo.domain.Board;

public interface CreactUseCase {
    Board create(BoardDto boardDto);
}
