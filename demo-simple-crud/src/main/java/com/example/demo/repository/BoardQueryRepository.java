package com.example.demo.repository;

import com.example.demo.controller.dto.BoardDto.ReadBoard;
import com.example.demo.domain.BoardEntity;
import com.example.demo.domain.BoardStatus;
import com.example.demo.domain.QBoardEntity;
import com.example.demo.mapper.BoardDtoMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final BoardDtoMapper boardDtoMapper;

    QBoardEntity qBoardEntity = QBoardEntity.boardEntity;

    @Transactional(readOnly = true)
    public Page<BoardEntity> readAll(Pageable pageable) {
        var content = jpaQueryFactory
                .selectFrom(qBoardEntity)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                jpaQueryFactory
                        .select(qBoardEntity.count())
                        .from(qBoardEntity)
                        .fetchOne()
        ).orElse(0L);

//        var pageList = content.stream()
//                .map(boardDtoMapper::toDomain)
//                .map(ReadBoard::new)
//                .toList();

        return new PageImpl<>(content, pageable, total);
    }


    @Transactional(readOnly = true)
    public BoardEntity readOne(Long id) {
        return jpaQueryFactory
                .selectFrom(qBoardEntity)
                .where(qBoardEntity.id.eq(id).and(qBoardEntity.status.ne(BoardStatus.REMOVED)))
                .fetchOne();
    }
}
