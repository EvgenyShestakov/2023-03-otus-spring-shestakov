package ru.otus.homework.service;

import ru.otus.homework.domain.Question;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

public class IOServiceStreams implements IOService {
    private final PrintStream output;

    private final Scanner input;

    public IOServiceStreams(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    @Override
    public void outputString(String s) {
        output.println(s);
    }

    @Override
    public void outputQuestions(Collection<Question> questions) {
        questions.forEach(question -> {
            outputString(question.getDescription());
            outputString("Select an answer with a number: ");
            question.getAnswers().stream().
                    map(answer -> String.format("%s. %s", answer.getPossibleAnswer(),
                            answer.getDescription())).forEach(this::outputString);
            outputString("");
        });
    }

    @Override
    public int readInt() {
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public int readIntWithPrompt(String prompt) {
        outputString(prompt);
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        outputString(prompt);
        return input.nextLine();
    }
}
