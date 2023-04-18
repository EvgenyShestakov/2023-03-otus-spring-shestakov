package ru.otus.homework.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.QuestionsCSVParser;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final QuestionsCSVParser csvParser;

    @Override
    public List<Question> findAll() {
        return csvParser.parse();
    }
}
