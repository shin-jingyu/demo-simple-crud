package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class Board {
    private Long id;
    private String title;
    private String content;
    private BoardStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
