package com.example.demo.mapper;

import com.example.demo.controller.dto.BoardDto;
import com.example.demo.domain.Board;
import org.mapstruct.Mapper;

@Mapper
public interface BoardEntityMapper {

    Board toEntity(BoardDto boardDto);
}
