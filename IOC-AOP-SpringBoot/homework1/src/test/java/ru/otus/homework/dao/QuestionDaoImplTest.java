package ru.otus.homework.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuestionDaoImplTest {
    @Mock
    private QuestionDaoImpl questionDao;

    @Test
    void getAllMustHaveSizeOne() {
        List<Answer> answers = List.of(new Answer(1, "Answer1", true));
        given(questionDao.findAll())
                .willReturn(List.of(new Question("Question", answers)));
        assertThat(questionDao.findAll()).hasSize(1);
    }
}
