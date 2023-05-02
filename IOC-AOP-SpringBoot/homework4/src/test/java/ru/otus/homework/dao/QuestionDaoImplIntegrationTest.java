package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.otus.Application;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@TestPropertySource(locations = "classpath:application-test.yaml",
        properties = "spring.shell.interactive.enabled=false")
class QuestionDaoImplIntegrationTest {
    @Autowired
    private QuestionDao questionDao;

    @Test
    void getAllMustHaveSizeFive() {
        assertThat(questionDao.findAll()).hasSize(5);
    }
}
