package com.example.demo.controller;

import com.example.demo.controller.dto.BoardDto;
import com.example.demo.controller.dto.BoardDto.ReadBoard;
import com.example.demo.controller.dto.BoardDto.UpdateBoard;
import com.example.demo.controller.dto.BoardDto.CreateBoard;
import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardDtoMapper;
import com.example.demo.usecase.CreactUseCase;
import com.example.demo.usecase.DeleteUseCase;
import com.example.demo.usecase.ReadAllUseCase;
import com.example.demo.usecase.ReadOneUseCase;
import com.example.demo.usecase.UpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardDtoMapper boardDtoMapper;

    private final CreactUseCase creactUseCase;
    private final DeleteUseCase deleteUseCase;
    private final UpdateUseCase updateUseCase;
    private final ReadOneUseCase readOneUseCase;
    private final ReadAllUseCase readAllUseCase;

    @GetMapping("/{boardId}")
    public ReadBoard readBoard(@PathVariable("boardId") Long boardId) {
        return readOneUseCase.readOne(boardId);
    }

    @GetMapping
    public Page<ReadBoard> readBoards(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC ) Pageable pageable) {
        return readAllUseCase.readAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBoard save(@Validated @RequestBody CreateBoard createBoard) {
        return boardDtoMapper.toBoardCreateDto(creactUseCase.create(createBoard));
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBoard(@PathVariable("boardId") Long boardId) {
        deleteUseCase.delete(boardId);
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateBoard update(@Validated @RequestBody UpdateBoard updateBoard, @PathVariable("boardId") Long boardId) {
        return boardDtoMapper.toBoardUpdateDto(updateUseCase.update(updateBoard, boardId));
    }
}
