package ru.otus.homework.dao;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.otus.homework.domain.Question;
import ru.otus.homework.utils.CSVParser;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private CSVParser csvParser;

    @Override
    public List<Question> findAll() {
        return csvParser.parse();
    }
}
