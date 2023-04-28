package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.Application;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
class QuestionDaoImplIntegrationTest {
    @Autowired
    private QuestionDao questionDao;

    @Test
    void getAllMustHaveSizeFive() {
        assertThat(questionDao.findAll()).hasSize(5);
    }
}
