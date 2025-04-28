package com.example.demo.controller.dto;

import com.example.demo.domain.Board;
import com.example.demo.domain.BoardStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class BoardDto {

    private BoardDto() {}

    public record CreateBoard(
            @NotBlank(message = "제목을 입력해 주세요.")
            @Size(min = 2, message = "2글자 이상 입력해주세요.")
            String title,
            @NotBlank(message = "내용을 입력해 주세요.")
            @Size(min = 2, message = "2글자 이상 입력해주세요.")
            String content
    ) {}

    public record UpdateBoard(
            @NotBlank(message = "제목을 입력해 주세요.")
            @Size(min = 2, message = "2글자 이상 입력해주세요.")
            String title,
            @NotBlank(message = "내용을 입력해 주세요.")
            @Size(min = 2, message = "2글자 이상 입력해주세요.")
            String content
    ) {}

    public record ReadBoard(
            Board board
    ) {}
}
