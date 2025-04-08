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
    private static final String DIRECTORY_COLOR = AnsiOutput.toString(AnsiColor.CYAN, AnsiColor.BRIGHT);
    private static final String FILE_COLOR = AnsiOutput.toString(AnsiColor.BLUE, AnsiColor.BRIGHT);
    private static final String RESET_COLOR = AnsiOutput.toString(AnsiColor.DEFAULT);

    @ShellMethod("Lists files in the specified directory, or in the current directory as default.")
    public String ls(@ShellOption(defaultValue = ShellOption.NULL) String path) {
        File directory = Optional.ofNullable(path)
                .map(File::new)
                .orElse(new File("."));

        if (!directory.exists() || !directory.isDirectory()) {
            return "Invalid directory.";
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return "Directory is empty.";
        }

        return Arrays.stream(files)
                .map(this::formatFile)
                .collect(Collectors.joining("\n"));
    }

    private String formatFile(File file) {
        String emoji = file.isDirectory() ? "🤖" : "🦄";  // Keeping original emojis
        String color = file.isDirectory() ? DIRECTORY_COLOR : FILE_COLOR;
        String name = file.getName() + (file.isDirectory() ? "/" : "");
        return color + emoji + " " + name + RESET_COLOR;  // Added space after emoji for better readability
    }
}