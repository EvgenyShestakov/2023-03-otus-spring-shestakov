package ru.otus.homework.service.processors;

public interface OutputService<E> {
    void outputString(String s);

    void outputObject(E object);
}
