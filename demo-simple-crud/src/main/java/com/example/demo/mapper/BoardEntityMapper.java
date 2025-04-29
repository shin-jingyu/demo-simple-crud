package com.example.demo.mapper;

import com.example.demo.controller.dto.BoardDto.UpdateBoard;
import com.example.demo.controller.dto.BoardDto.CreateBoard;
import com.example.demo.domain.Board;
import com.example.demo.domain.BoardEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardEntityMapper {
    BoardEntity toCreateEntity(CreateBoard boardDto);
    BoardEntity toUpdateEntity(UpdateBoard boardDto);
    BoardEntity toEntity(Board board);
}
