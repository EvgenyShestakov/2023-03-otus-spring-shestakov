package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.util.List;

public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuestionDao questionDao;
    private final IOService ioService;

    public ApplicationRunnerImpl(QuestionDao questionDao, IOService ioService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
    }

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        questions.forEach(question -> {
            ioService.outputString(question.getDescription());
            ioService.outputString("Select an answer with a number: ");
            question.getAnswers().stream().
                    map(answer -> String.format("%s. %s", answer.getPossibleAnswer(),
                            answer.getDescription())).forEach(ioService::outputString);
            ioService.outputString("");
        });
    }
}
