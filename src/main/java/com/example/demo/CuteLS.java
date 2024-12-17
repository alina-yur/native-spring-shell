package com.example.demo;

import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class CuteLS {
    private static final String DIRECTORY_COLOR = "\033[1;36m";
    private static final String FILE_COLOR = "\033[1;34m";
    private static final String RESET_COLOR = AnsiOutput.toString(AnsiColor.DEFAULT);

    @ShellMethod("Lists files in the specified directory, or in the current directory as default.")
    public String ls(@ShellOption(defaultValue = ShellOption.NULL) String path) {
        File directory = Optional.ofNullable(path)
            .map(File::new)
            .orElse(new File("."));
        
        if (!directory.exists() || !directory.isDirectory()) {
            return "Invalid directory.";
        }
        
        return Optional.ofNullable(directory.listFiles())
            .map(files -> Arrays.stream(files)
                .map(this::formatFile)
                .collect(Collectors.joining("\n")))
            .orElse("");
    }

    private String formatFile(File file) {
        String emoji = file.isDirectory() ? "🤖" : "🦄";
        String color = file.isDirectory() ? DIRECTORY_COLOR : FILE_COLOR;
        String name = file.getName() + (file.isDirectory() ? "/" : "");
        return color + emoji + name + RESET_COLOR;
    }
}