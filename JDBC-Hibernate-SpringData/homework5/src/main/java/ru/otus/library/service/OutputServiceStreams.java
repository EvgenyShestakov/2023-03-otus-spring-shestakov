package ru.otus.library.service;

import lombok.RequiredArgsConstructor;

import java.io.PrintStream;

@RequiredArgsConstructor
public class OutputServiceStreams implements OutputService {
    private final PrintStream output;

    @Override
    public void outputString(String s) {
        output.println(s);
    }
}
