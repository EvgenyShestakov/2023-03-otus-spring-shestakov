package ru.otus.homework.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private String filepath;

    private int testScore;

    private Locale locale;

    @PostConstruct
    public void init() {
        String[] path = filepath.split("\\.");
        this.filepath = String.format("%s_%s.%s", path[0], locale.getLanguage(), path[1]);
    }
}
