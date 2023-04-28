package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.provider.TestScoreProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuestionDao questionDao;

    private final IOService ioService;

    private final LocalizationService localizationService;

    private final TestScoreProvider testScoreProvider;

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        String fullName = ioService.readStringWithPrompt(localizationService.
                getLocalizationMessage("enter.your.fullName"));
        int testingResult = testing(questions);
        String result = formResult(fullName, testingResult);
        ioService.outputString(result);
    }

    private int testing(List<Question> questions) {
        int count = 0;
        int index = 0;
        while (index < questions.size()) {
            Question question = questions.get(index);
            displayQuestion(question);
            int answerIndex = ioService.readIntWithPrompt(question.getAnswers().size(),
                    localizationService.getLocalizationMessage("select.an.answer.with.a.number"));
            if (answerIndex != -1) {
                Answer answer = question.getAnswers().get(answerIndex);
                count = answer.isCorrect() ? count + 1 : count;
                index++;
            } else {
                ioService.outputString(localizationService.
                        getLocalizationMessage("entered.value.is.outside"));
            }
            ioService.outputString("");
        }
        return count;
    }

    private void displayQuestion(Question question) {
        ioService.outputString(question.getDescription());
        ioService.outputString(localizationService.
                getLocalizationMessage("select.an.answer.with.a.number"));
        question.getAnswers().stream().
                map(answer -> String.format("%s. %s", answer.getPossibleAnswer(),
                        answer.getDescription())).forEach(ioService::outputString);
        ioService.outputString("");
    }

    private String formResult(String fullName, int testingResult) {
        return testingResult >= testScoreProvider.getTestScore() ? localizationService.
                getLocalizationMessage("test.passed", fullName, testingResult) : localizationService.
                getLocalizationMessage("test.failed", fullName, testingResult);
    }
}


