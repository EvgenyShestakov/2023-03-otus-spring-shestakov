package ru.otus.homework.service;

public interface LocalizationService {
    String getLocalizationMessage(String code);

    String getLocalizationMessage(String code, Object... args);
}
