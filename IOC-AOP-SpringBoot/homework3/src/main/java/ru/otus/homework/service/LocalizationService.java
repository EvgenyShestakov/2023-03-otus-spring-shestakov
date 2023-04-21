package ru.otus.homework.service;

public interface LocalizationService {
    String enterYourName();

    String selectAnswer();

    String goOutOfRange();

    String testPassed(String name, int grade);

    String testFailed(String name, int grade);
}
