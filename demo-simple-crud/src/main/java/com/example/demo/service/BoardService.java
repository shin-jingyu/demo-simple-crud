package com.example.demo.service;

import com.example.demo.controller.dto.BoardDto;
import com.example.demo.controller.dto.BoardDto.ReadBoard;
import com.example.demo.controller.dto.BoardDto.UpdateBoard;
import com.example.demo.controller.dto.BoardDto.CreateBoard;
import com.example.demo.domain.Board;
import com.example.demo.domain.BoardStatus;
import com.example.demo.mapper.BoardDtoMapper;
import com.example.demo.mapper.BoardEntityMapper;
import com.example.demo.repository.BoardQueryRepository;
import com.example.demo.repository.BoardRepository;
import com.example.demo.usecase.CreactUseCase;
import com.example.demo.usecase.DeleteUseCase;
import com.example.demo.usecase.ReadAllUseCase;
import com.example.demo.usecase.ReadOneUseCase;
import com.example.demo.usecase.UpdateUseCase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BoardService implements CreactUseCase, UpdateUseCase, DeleteUseCase, ReadOneUseCase, ReadAllUseCase {

    private final BoardQueryRepository boardQueryRepository;
    private final BoardRepository boardRepository;
    private final BoardEntityMapper boardEntityMapper;
    private final BoardDtoMapper boardDtoMapper;

    @Override
    public Board create(CreateBoard createBoard) {
        var boardEntity = boardEntityMapper.toCreateEntity(createBoard);
        var board = boardEntity.toBuilder()
                .status(BoardStatus.ACTIVE)
                .build();
        return boardDtoMapper.toDomain(boardRepository.save(board));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var board = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));;
        board.update(BoardStatus.REMOVED);
    }

    @Override
    @Transactional
    public Board update(UpdateBoard updateBoard, Long id) {
        var board = boardEntityMapper.toUpdateEntity(updateBoard);
        var existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        existingBoard.update(board.getTitle(), board.getContent());
        return boardDtoMapper.toDomain(existingBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReadBoard> readAll(Pageable pageable) {
        var boards = boardQueryRepository.readAll(pageable).map(boardDtoMapper::toDomain);
        return boards.map(ReadBoard::new);
    }

    @Override
    @Transactional(readOnly = true)
    public ReadBoard readOne(Long id) {
        var boardEntity = boardQueryRepository.readOne(id);
        if (boardEntity == null) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
        return boardDtoMapper.toBoardReadDto(boardDtoMapper.toDomain(boardEntity));
    }
}
