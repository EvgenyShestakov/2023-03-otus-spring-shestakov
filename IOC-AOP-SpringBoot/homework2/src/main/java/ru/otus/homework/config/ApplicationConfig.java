package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.IOServiceStreams;

@Configuration
public class ApplicationConfig {
    @Bean
    public IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
