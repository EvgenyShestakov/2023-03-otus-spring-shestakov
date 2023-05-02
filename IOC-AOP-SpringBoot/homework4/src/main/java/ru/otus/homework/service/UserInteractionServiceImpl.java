package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInteractionServiceImpl implements UserInteractionService {
    private final IOService ioService;

    private final LocalizationService localizationService;

    @Override
    public int selectAnswer(int size) {
        return ioService.readIntWithPrompt(size,
                localizationService.getLocalizationMessage("select.answer.with.number"));
    }

    @Override
    public void enterValueFromSpecifiedRange() {
        ioService.outputString(localizationService.
                getLocalizationMessage("entered.value.is.outside"));
    }

    @Override
    public String testPassed(String fullName, int testingResult) {
        return localizationService.
                getLocalizationMessage("test.passed", fullName, testingResult);
    }

    @Override
    public String testFailed(String fullName, int testingResult) {
        return localizationService.
                getLocalizationMessage("test.failed", fullName, testingResult);
    }
}


