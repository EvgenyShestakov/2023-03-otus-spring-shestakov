package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

@Service
public class ApplicationRunnerImpl implements ApplicationRunner {
    private QuestionDao questionDao;

    private IOService<Question> ioService;

    private int passingTestScore;

    public ApplicationRunnerImpl(QuestionDao questionDao, IOService<Question> ioService,
                                 @Value("${passing.test.score}") int passingTestScore) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.passingTestScore = passingTestScore;
    }

    @Override
    public void run() {
        List<Question> questions = questionDao.findAll();
        String fullName = ioService.readStringWithPrompt("Enter your fullName");
        int count = 0;
        int index = 0;
        while (index < questions.size()) {
            Question question = questions.get(index);
            ioService.outputObject(question);
            try {
                int number = ioService.readIntWithPrompt("Select an answer with a number: ");
                int answerIndex = number - 1;
                if (answerIndex >= 0 && answerIndex < question.getAnswers().size()) {
                    Answer answer = question.getAnswers().get(answerIndex);
                    count = answer.isCorrect() ? count + 1 : count;
                    index++;
                } else {
                    ioService.outputString("The entered number is not within the range of valid values");
                }
            } catch (NumberFormatException e) {
                ioService.outputString("The entered value is not an integer");
            }
            ioService.outputString("");
        }
        ioService.outputString(formResult(fullName, count));
    }

    public String formResult(String fullName, int count) {
        String result = count >= passingTestScore ? "%s - test passed, grade %d" : "%s - test failed, grade %d";
        return String.format(result, fullName, count);
    }
}


