package ru.otus.homework.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.homework.provider.LocalProvider;
import ru.otus.homework.provider.ResourceProvider;
import ru.otus.homework.provider.TestScoreProvider;

import java.util.Locale;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps implements LocalProvider, ResourceProvider, TestScoreProvider {
    private String resourcePath;

    private int testScore;

    private Locale locale;

    @PostConstruct
    public void init() {
        String[] path = resourcePath.split("\\.");
        this.resourcePath = String.format("%s_%s.%s", path[0], locale.getLanguage(), path[1]);
    }
}
