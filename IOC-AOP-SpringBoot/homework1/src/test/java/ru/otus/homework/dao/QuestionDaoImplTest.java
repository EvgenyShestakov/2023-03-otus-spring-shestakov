package ru.otus.homework.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {
    @Mock
    private QuestionDaoImpl questionDao;

//    private QuestionServiceImpl questionService;
//
//    @BeforeEach
//    void setUp() {
//        questionService = new QuestionServiceImpl(questionDao);
//    }
//
//    @Test
//    void getAllMustHaveSizeOne() {
//        Map<String, Option> optionMap = Map.of("1", new Option("Answer1", true));
//        given(questionDao.findAll())
//                .willReturn(List.of(new Question("Question", optionMap)));
//        assertThat(questionService.findAll()).hasSize(1);
//    }
}
