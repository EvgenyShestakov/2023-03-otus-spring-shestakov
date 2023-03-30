package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import ru.otus.homework.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class ProcessorServiceImpl implements ProcessorService {
    private final QuestionService questionService;

    @Override
    public void process() {
        List<Question> questions = questionService.findAll();
        questions.forEach(question -> {
                    System.out.println(question.getDescription());
                    System.out.println("Select an answer with a number:");
                    question.getOptions().entrySet().stream().
                            map(entry -> String.format("%s. %s", entry.getKey(), entry.
                                    getValue().getDescription())).
                            forEach(System.out::println);
                    System.out.println();
                }
        );
    }
}
