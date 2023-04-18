package ru.otus.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.homework.service.ApplicationRunner;

@PropertySource("classpath:application.properties")
@ComponentScan("ru.otus.homework")
@Configuration
public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        ApplicationRunner runner = context.getBean(ApplicationRunner.class);
        runner.run();
    }
}
