package ru.otus.homework.service;

import ru.otus.homework.service.processors.InputService;
import ru.otus.homework.service.processors.OutputService;

public interface IOService<E> extends InputService, OutputService<E> {
}
