package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuestionDao questionDao;

    private final IOService ioService;

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        ioService.outputQuestions(questions);
    }
}
