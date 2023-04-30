package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.homework.provider.LocaleProvider;
import ru.otus.homework.provider.ResourceProvider;
import ru.otus.homework.provider.TestScoreProvider;

import java.util.Locale;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps implements LocaleProvider, ResourceProvider, TestScoreProvider {
    private String resourcePath;

    private int testScore;

    private Locale locale;

    public String getResourcePath() {
        String[] path = resourcePath.split("\\.");
        return String.format("%s_%s.%s", path[0], locale.getLanguage(), path[1]);
    }
}
