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

    @Override
    public String getLocalizationMessage(String code) {
        return this.messageSource.getMessage(code, null, appProps.getLocale());
    }

    @Override
    public String getLocalizationMessage(String code, Object... args) {
        return this.messageSource.getMessage(code, args, appProps.getLocale());
    }
}

