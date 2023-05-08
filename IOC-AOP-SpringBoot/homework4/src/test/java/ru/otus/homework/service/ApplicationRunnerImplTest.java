package ru.otus.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.holder.UserNameHolder;
import ru.otus.homework.provider.ResourceProvider;
import ru.otus.homework.provider.TestScoreProvider;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yaml",
        properties = "spring.shell.interactive.enabled=false")
class ApplicationRunnerImplTest {
    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private IOService ioService;

    @MockBean
    private LocalizationService localizationService;

    @MockBean
    private ResourceProvider resourceProvider;

    @MockBean
    private UserInteractionService userInteractionService;

    @MockBean
    private TestScoreProvider testScoreProvider;

    @MockBean
    private UserNameHolder userNameHolder;

    @Autowired
    private ApplicationRunner applicationRunner;

    @Test
    void testPassed() {
        List<Question> questions = List.of(
                new Question("What color is the sky?", List.of(
                        new Answer(1, "blue", true),
                        new Answer(2, "red", false))));
        given(userNameHolder.getUserName()).willReturn("John Connor");
        given(userInteractionService.selectAnswer(1)).willReturn(0);
        given(userInteractionService.testPassed("John Connor", 1)).
                willReturn("John Connor - test passed, grade 1");
        given(questionDao.findAll()).willReturn(questions);
        applicationRunner.run();
        verify(ioService).outputString("John Connor - test passed, grade 1");
    }

    @Test
    void testFailed() {
        List<Question> questions = List.of(
                new Question("What is the capital of France?", List.
                        of(new Answer(1, "London", false),
                                new Answer(2, "Paris", true))));
        given(testScoreProvider.getTestScore()).willReturn(1);
        given(userNameHolder.getUserName()).willReturn("John Connor");
        given(userInteractionService.selectAnswer(1)).willReturn(0);
        given(userInteractionService.testFailed("John Connor", 0)).
                willReturn("John Connor - test failed, grade 0");
        given(questionDao.findAll()).willReturn(questions);
        applicationRunner.run();
        verify(ioService).outputString("John Connor - test failed, grade 0");
    }
}
