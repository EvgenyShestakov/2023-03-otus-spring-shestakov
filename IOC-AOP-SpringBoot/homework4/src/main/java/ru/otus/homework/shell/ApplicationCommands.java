package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.holder.UserNameHolder;
import ru.otus.homework.service.ApplicationRunner;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final ApplicationRunner runner;

    private final UserNameHolder userNameHolder;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "User") String userName) {
        userNameHolder.setUserName(userName);
    }

    @ShellMethod(value = "run-test", key = {"r", "run"})
    @ShellMethodAvailability(value = "isRunTestAvailable")
    public void run() {
        runner.run();
    }

    private Availability isRunTestAvailable() {
        return userNameHolder.getUserName() == null ? Availability.
                unavailable("Login first") : Availability.available();
    }
}
