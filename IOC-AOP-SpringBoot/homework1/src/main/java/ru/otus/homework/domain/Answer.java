package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private int possibleAnswer;

    private String description;

    private boolean correct;
}
