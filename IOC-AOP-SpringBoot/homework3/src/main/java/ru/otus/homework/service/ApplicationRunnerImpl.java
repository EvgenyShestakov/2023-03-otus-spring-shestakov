package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.config.AppProps;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuestionDao questionDao;

    private final IOService ioService;

    private final LocalizationService localization;

    private final int passingTestScore;

    public ApplicationRunnerImpl(QuestionDao questionDao, IOService ioService,
                                 LocalizationService localization, AppProps appProps) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.localization = localization;
        this.passingTestScore = appProps.getTestScore();
    }

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        String fullName = ioService.readStringWithPrompt(localization.enterYourName());
        int testingResult = testing(questions);
        ioService.outputString(formResult(fullName, testingResult));
    }

    private int testing(List<Question> questions) {
        int count = 0;
        int index = 0;
        while (index < questions.size()) {
            Question question = questions.get(index);
            displayQuestion(question);
            int answerIndex = ioService.readIntWithPrompt(question.getAnswers().size(),
                    localization.selectAnswer());
            if (answerIndex != -1) {
                Answer answer = question.getAnswers().get(answerIndex);
                count = answer.isCorrect() ? count + 1 : count;
                index++;
            } else {
                ioService.outputString(localization.goOutOfRange());
            }
            ioService.outputString("");
        }
        return count;
    }

    private void displayQuestion(Question question) {
        ioService.outputString(question.getDescription());
        ioService.outputString(localization.selectAnswer());
        question.getAnswers().stream().
                map(answer -> String.format("%s. %s", answer.getPossibleAnswer(),
                        answer.getDescription())).forEach(ioService::outputString);
        ioService.outputString("");
    }

    private String formResult(String fullName, int testingResult) {
        return testingResult >= passingTestScore ? localization.
                testPassed(fullName, testingResult) : localization.testFailed(fullName, testingResult);
    }
}


