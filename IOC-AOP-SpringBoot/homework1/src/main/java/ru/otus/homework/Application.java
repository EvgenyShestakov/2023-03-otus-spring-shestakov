package ru.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.ApplicationRunner;

public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        ApplicationRunner runner = context.getBean(ApplicationRunner.class);
        runner.run();
    }
}
