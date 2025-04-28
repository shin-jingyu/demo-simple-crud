package com.example.demo.service;

import com.example.demo.controller.dto.BoardDto;
import com.example.demo.domain.Board;
import com.example.demo.domain.BoardStatus;
import com.example.demo.mapper.BoardDtoMapper;
import com.example.demo.mapper.BoardEntityMapper;
import com.example.demo.repository.BoardRepository;
import com.example.demo.usecase.CreactUseCase;
import com.example.demo.usecase.DeleteUseCase;
import com.example.demo.usecase.ReadAllUseCase;
import com.example.demo.usecase.ReadOneUseCase;
import com.example.demo.usecase.UpdateUseCase;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService implements CreactUseCase, UpdateUseCase, DeleteUseCase, ReadOneUseCase, ReadAllUseCase {

    private final BoardRepository boardRepository;
    private final BoardEntityMapper boardEntityMapper;

    @Override
    public Board create(BoardDto boardDto) {
        var board = boardEntityMapper.toEntity(boardDto);
        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var boardId = boardRepository.existsById(id);
        if(boardId) {
            var board = boardRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Board not found"));;
            board.statusUpdate(BoardStatus.REMOVED);
        }

    }

    @Override
    @Transactional
    public Board update(BoardDto boardDto) {
        var board = boardEntityMapper.toEntity(boardDto);
        var boardExists = boardRepository.existsById(board.getId());

        if (boardExists) {
            var existingBoard = boardRepository.findById(board.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Board not found"));

            existingBoard.update(
                    board.getTitle(),
                    board.getContent(),
                    board.getStatus() != null ?  board.getStatus() : BoardStatus.ACTIVE
            );

            return existingBoard;
        }
        throw new EntityNotFoundException("Board not found");
    }

    @Override
    public Page<Board> readAll(Pageable pageable) {
        return null;
    }

    @Override
    public Board readOne(Long id) {
        return null;
    }
}
