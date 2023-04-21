package ru.otus.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.DelegatingMessageSource;
import ru.otus.homework.config.AppProps;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class ApplicationRunnerImplTest {
    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @Mock
    private AppProps appProps = new AppProps();

    @Mock
    private LocalizationService localization = new LocalizationServiceImpl(appProps, new DelegatingMessageSource());

    private ApplicationRunner applicationRunner;

    @BeforeEach
    void setUp() {
        applicationRunner = new ApplicationRunnerImpl(questionDao, ioService, localization, appProps);
    }

    @Test
    void testPassed() {
        List<Question> questions = List.of(
                new Question("What color is the sky?", List.of(
                        new Answer(1, "blue", true),
                        new Answer(2, "red", false))));
        given(localization.enterYourName()).willReturn("Enter your fullName: ");
        given(localization.selectAnswer()).willReturn("Select an answer with a number: ");
        given(localization.testPassed("John Connor", 1)).willReturn("John Connor - test passed, grade 1");
        given(ioService.readStringWithPrompt(anyString())).willReturn("John Connor");
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
        given(localization.enterYourName()).willReturn("Enter your fullName: ");
        given(localization.selectAnswer()).willReturn("Select an answer with a number: ");
        given(localization.testPassed("John Connor", 0)).willReturn("John Connor - test failed, grade 0");
        given(ioService.readStringWithPrompt(anyString())).willReturn("John Connor");
        given(questionDao.findAll()).willReturn(questions);
        applicationRunner.run();
        verify(ioService).outputString("John Connor - test failed, grade 0");
    }
}
