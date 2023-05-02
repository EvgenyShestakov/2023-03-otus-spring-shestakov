package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String description;

    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer possibleAnswer) {
        answers.add(possibleAnswer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
