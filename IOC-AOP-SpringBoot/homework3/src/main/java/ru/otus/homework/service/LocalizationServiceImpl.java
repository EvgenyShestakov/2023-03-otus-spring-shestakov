package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.AppProps;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    private final AppProps appProps;

    private final MessageSource messageSource;

    public String enterYourName() {
        return messageSource.getMessage("enter.your.fullName", new Object[0], appProps.getLocale());
    }

    public String selectAnswer() {
        return messageSource.getMessage("select.an.answer.with.a.number", new Object[0], appProps.getLocale());
    }

    public String goOutOfRange() {
        return messageSource.getMessage("entered.value.is.outside", new Object[0], appProps.getLocale());
    }

    public String testPassed(String name, int grade) {
        return messageSource.getMessage("test.passed", new Object[]{name, grade}, appProps.getLocale());
    }

    public String testFailed(String name, int grade) {
        return messageSource.getMessage("test.failed", new Object[]{name, grade}, appProps.getLocale());
    }
}

