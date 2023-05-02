package ru.otus.homework.utils;

import au.com.bytecode.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exception.WhileLoadingQuestionsException;
import ru.otus.homework.provider.ResourceProvider;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionsCSVParser {
    private final ResourceProvider resourceProvider;

    public List<Question> parse() {
        List<Question> questions;
        try (InputStream inputStream = getClass().getClassLoader().
                getResourceAsStream(resourceProvider.getResourcePath())) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found");
            }
            CSVReader reader = new CSVReader(new InputStreamReader((inputStream)), ';');
            questions = readingLinesOfCSVFile(reader);
        } catch (Exception e) {
            throw new WhileLoadingQuestionsException("CSV parsing error", e);
        }
        return questions;
    }

    private List<Question> readingLinesOfCSVFile(CSVReader reader) throws Exception {
        List<Question> questions = new ArrayList<>();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            String currentString = nextLine[0];
            if (currentString.isBlank()) {
                break;
            }
            Question question = new Question();
            question.setDescription(nextLine[0]);
            for (int i = 1; i < nextLine.length; i++) {
                String[] mass = nextLine[i].split("=");
                question.addAnswer(Answer.builder().
                        possibleAnswer(i).description(mass[0]).
                        correct(Boolean.parseBoolean(mass[1])).
                        build());
            }
            questions.add(question);
        }
        return questions;
    }
}
