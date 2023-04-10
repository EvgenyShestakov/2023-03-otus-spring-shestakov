package ru.otus.homework.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.QuestionsCSVParser;

import java.util.List;

@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final QuestionsCSVParser csvParser;

    @Override
    public List<Question> findAll() {
        return csvParser.parse();
    }
}
