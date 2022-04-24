package com.mark.spring.shell.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ShellComponent
public class TerminalCommand {
    @ShellMethod("matcher")
    public String matcher(@ShellOption(value = "-R") String regex, @ShellOption(value = "-V") String value) {
        Matcher matcher = Pattern.compile(regex).matcher(value);
        boolean matchResult = matcher.find();

        if (!matchResult) {
            return "\n\r";
        }

        int groupCount = matcher.groupCount();
        if (groupCount == 0) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            StringBuilder builder = new StringBuilder(value);
            String result = "\n\r" + builder.substring(startIndex, endIndex);
            return result;
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < groupCount; i++) {
            result.add(matcher.group(i));
        }

        return result.toString();
    }



    @ShellMethod("ls")
    public String ls(String paths) {
        File file = new File("./" + paths);
        return Arrays.toString(file.list());
    }
}
