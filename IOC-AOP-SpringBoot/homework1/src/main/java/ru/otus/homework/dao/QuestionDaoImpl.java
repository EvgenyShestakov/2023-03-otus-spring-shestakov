package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {
    private List<Question> questions = new ArrayList<>();

    @Override
    public List<Question> findAll() {
        return questions;
    }

    @Override
    public void saveAll(List<Question> questions) {
        this.questions.addAll(questions);
    }
}
