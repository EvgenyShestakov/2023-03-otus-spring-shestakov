package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class ApplicationRunnerImpl implements ApplicationRunner {
    private final QuestionDao questionDao;

    private final IOService ioService;

    private final int passingTestScore;

    public ApplicationRunnerImpl(QuestionDao questionDao, IOService ioService,
                                 @Value("${passing.test.score}") int passingTestScore) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.passingTestScore = passingTestScore;
    }

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        String fullName = ioService.readStringWithPrompt("Enter your fullName");
        int testingResult = testing(questions);
        ioService.outputString(formResult(fullName, testingResult));
    }

    private int testing(List<Question> questions) {
        int count = 0;
        int index = 0;
        while (index < questions.size()) {
            Question question = questions.get(index);
            displayQuestion(question);
            int answerIndex = readInt(question.getAnswers().size());
            if (answerIndex != -1) {
                Answer answer = question.getAnswers().get(answerIndex);
                count = answer.isCorrect() ? count + 1 : count;
                index++;
            } else {
                ioService.outputString("The entered value is outside the range of response numbers");
            }
            ioService.outputString("");
        }
        return count;
    }

    private int readInt(int size) {
        int answerIndex;
        try {
            int number = ioService.readIntWithPrompt("Select an answer with a number: ");
            answerIndex = number - 1;
            answerIndex = answerIndex >= 0 && answerIndex < size ? answerIndex : -1;
        } catch (NumberFormatException e) {
            answerIndex = -1;
        }
        return answerIndex;
    }

    private void displayQuestion(Question question) {
        ioService.outputString(question.getDescription());
        ioService.outputString("Select an answer with a number: ");
        question.getAnswers().stream().
                map(answer -> String.format("%s. %s", answer.getPossibleAnswer(),
                        answer.getDescription())).forEach(ioService::outputString);
        ioService.outputString("");
    }

    private String formResult(String fullName, int testingResult) {
        String result = testingResult >= passingTestScore ? "%s - test passed, grade %d" : "%s - test failed, grade %d";
        return String.format(result, fullName, testingResult);
    }
}


