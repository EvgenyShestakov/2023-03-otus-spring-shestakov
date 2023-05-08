package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {
    private long id;

    private String genreName;

    public Genre(long id) {
        this.id = id;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
