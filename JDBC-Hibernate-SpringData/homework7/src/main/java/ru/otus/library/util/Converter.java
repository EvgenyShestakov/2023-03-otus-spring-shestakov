package ru.otus.library.util;

import java.util.List;

public interface Converter<T> {
    String convert(T domain);

    String convert(List<T> domains);
}
