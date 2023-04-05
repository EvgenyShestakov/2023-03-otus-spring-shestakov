package ru.otus.homework.utils;

import au.com.bytecode.opencsv.CSVReader;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;
import ru.otus.homework.exception.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSVParser {

    public List<Question> parse() {
        List<Question> questions = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("questions.csv")) {
            CSVReader reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(inputStream)), ';');
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
        } catch (IOException e) {
            throw new ParseException("CSV parsing error");
        }
        return questions;
    }
}
