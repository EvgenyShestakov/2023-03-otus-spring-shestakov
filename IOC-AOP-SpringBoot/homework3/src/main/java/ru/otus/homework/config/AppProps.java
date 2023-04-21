package ru.otus.homework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private String filepathRu;

    private String filepathEn;

    private int testScore;

    private Locale locale;

    public AppProps() {
        this.locale = Locale.getDefault();
    }
}
