package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.holder.UserNameHolder;
import ru.otus.homework.provider.TestScoreProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuestionDao questionDao;

    private final IOService ioService;

    private final UserInteractionService messageService;

    private final TestScoreProvider testScoreProvider;

    private final UserNameHolder userNameHolder;

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        int testingResult = testing(questions);
        String result = formResult(userNameHolder.getUserName(), testingResult);
        ioService.outputString(result);
    }

    private int testing(List<Question> questions) {
        int count = 0;
        int index = 0;
        while (index < questions.size()) {
            Question question = questions.get(index);
            displayQuestion(question);
            int answerIndex = messageService.selectAnswer(questions.size());
            if (answerIndex != -1) {
                Answer answer = question.getAnswers().get(answerIndex);
                count = answer.isCorrect() ? count + 1 : count;
                index++;
            } else {
                messageService.enterValueFromSpecifiedRange();
            }
            ioService.outputString("");
        }
        return count;
    }

    private void displayQuestion(Question question) {
        ioService.outputString(question.getDescription());
        question.getAnswers().stream().
                map(answer -> String.format("%s. %s", answer.getPossibleAnswer(),
                        answer.getDescription())).forEach(ioService::outputString);
        ioService.outputString("");
    }

    private String formResult(String fullName, int testingResult) {
        return testingResult >= testScoreProvider.getTestScore() ? messageService.
                testPassed(fullName, testingResult) : messageService.
                testFailed(fullName, testingResult);
    }
}


