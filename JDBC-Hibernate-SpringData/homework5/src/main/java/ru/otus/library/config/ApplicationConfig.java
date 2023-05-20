package ru.otus.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.library.service.OutputService;
import ru.otus.library.service.OutputServiceStreams;

@Configuration
public class ApplicationConfig {
    @Bean
    public OutputService ioService() {
        return new OutputServiceStreams(System.out);
    }
}
