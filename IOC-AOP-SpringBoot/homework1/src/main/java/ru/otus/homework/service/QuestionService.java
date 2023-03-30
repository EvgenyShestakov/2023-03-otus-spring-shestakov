package ru.otus.homework.service;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();

    void saveAll(List<Question> questions);
}
