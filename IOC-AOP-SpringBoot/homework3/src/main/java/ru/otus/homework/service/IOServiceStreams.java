package ru.otus.homework.service;

import java.io.InputStream;
import java.io.PrintStream;
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
    public int readInt() {
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public int readIntWithPrompt(int size, String prompt) {
        int index;
        try {
            int number = readIntWithPrompt(prompt);
            index = number - 1;
            index = index >= 0 && index < size ? index : -1;
        } catch (NumberFormatException e) {
            index = -1;
        }
        return index;
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
