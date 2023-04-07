package ru.otus.homework.service.processors;

import ru.otus.homework.domain.Question;

import java.util.Collection;

public interface OutputService {
    void outputString(String s);

    void outputQuestions(Collection<Question> questions);
}
