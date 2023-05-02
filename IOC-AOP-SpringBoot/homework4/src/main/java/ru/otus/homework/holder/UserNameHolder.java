package ru.otus.homework.holder;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class UserNameHolder {
    private String userName;
}
