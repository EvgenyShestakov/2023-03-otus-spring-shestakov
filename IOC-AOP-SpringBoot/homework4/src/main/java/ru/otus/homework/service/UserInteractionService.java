package ru.otus.homework.service;

public interface UserInteractionService {
    int selectAnswer(int size);

    void enterValueFromSpecifiedRange();

    String testPassed(String fullName, int testingResult);

    String testFailed(String fullName, int testingResult);
}


