package ru.otus.homework.service.processors;

public interface InputService {
    int readInt();

    int readIntWithPrompt(int size, String prompt);

    int readIntWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);
}
