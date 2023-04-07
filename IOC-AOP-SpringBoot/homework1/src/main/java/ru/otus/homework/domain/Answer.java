package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Answer {
    private int possibleAnswer;

    private String description;

    private boolean correct;
}
