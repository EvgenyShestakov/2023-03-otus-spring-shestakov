package ru.otus.homework.utils;

import au.com.bytecode.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Option;
import ru.otus.homework.domain.Question;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CSVParser {
    private final Resource resource;

    private final QuestionDao questionDao;

    public void parse() {
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()), ';')) {
            List<Question> questions = new ArrayList<>();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[0].isBlank()) {
                    break;
                }
                Question question = new Question();
                question.setDescription(nextLine[0]);
                for (int i = 1; i < nextLine.length; i++) {
                    String[] mass = nextLine[i].split("=");
                    question.getOptions().put(String.valueOf(i), Option.builder().
                            description(mass[0]).correct(Boolean.parseBoolean(mass[1])).build());
                }
                questions.add(question);
            }
            questionDao.saveAll(questions);
        } catch (IOException e) {
            System.out.println("CSV parsing error");
            e.printStackTrace();
        }
    }
}
