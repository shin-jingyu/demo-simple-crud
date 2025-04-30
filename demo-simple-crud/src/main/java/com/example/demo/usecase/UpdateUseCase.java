package com.example.demo.usecase;

import com.example.demo.controller.dto.BoardDto.UpdateBoard;
import com.example.demo.domain.Board;

public interface UpdateUseCase {
    Board update(UpdateBoard boardDto, Long id);
}
