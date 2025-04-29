package com.example.demo.mapper;

import com.example.demo.controller.dto.BoardDto.ReadBoard;
import com.example.demo.controller.dto.BoardDto.UpdateBoard;
import com.example.demo.controller.dto.BoardDto.CreateBoard;
import com.example.demo.domain.Board;
import com.example.demo.domain.BoardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardDtoMapper {
    Board toDomain(BoardEntity boardEntity);
    CreateBoard toBoardCreateDto(Board board);
    UpdateBoard toBoardUpdateDto(Board board);
    ReadBoard toBoardReadDto(Board board);
}
