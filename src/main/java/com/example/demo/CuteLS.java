package com.example.demo;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;

@ShellComponent
public class CuteLS {

    @ShellMethod("Lists files in the specified directory, or in the current directory if no path is provided.")
    public String ls(@ShellOption(defaultValue = ShellOption.NULL) String path) {
        File directory = path != null ? new File(path) : new File(".");
        
        if (!directory.exists() || !directory.isDirectory()) {
            return "Invalid directory.";
        }

        StringBuilder sb = new StringBuilder();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    sb.append(AnsiOutput.toString(AnsiColor.BLUE))
                      .append("\uD83D\uDCC1 ")
                      .append(file.getName())
                      .append("/")
                      .append(AnsiOutput.toString(AnsiColor.DEFAULT))
                      .append("\n");
                } else {
                    sb.append(AnsiOutput.toString(AnsiColor.BRIGHT_MAGENTA))
                      .append("\uD83E\uDD84 ")
                      .append(file.getName())
                      .append(AnsiOutput.toString(AnsiColor.DEFAULT))
                      .append("\n");
                }
            }
        }
        return sb.toString();
    }
}
