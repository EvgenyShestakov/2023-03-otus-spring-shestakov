package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoImpl;
import ru.otus.homework.utils.QuestionsCSVParser;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoImplIntegrationTest {
    private final QuestionsCSVParser parser = new QuestionsCSVParser("questions.csv");

    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(parser);
    }

    @Test
    void getAllMustHaveSizeFive() {
        assertThat(questionDao.findAll()).hasSize(5);
    }

    @Test
    void getAllMustBeNonEmpty() {
        Assertions.assertFalse(questionDao.findAll().isEmpty());
    }

    @Test
    void getAllMustBeNonNull() {
        Assertions.assertNotNull(questionDao.findAll());
    }
}
