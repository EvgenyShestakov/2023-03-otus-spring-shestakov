package ru.otus.library.util;

public interface Converter<T> {
    String convert(T domain);
}
