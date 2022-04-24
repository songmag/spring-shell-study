package com.mark.spring.shell;

import com.mark.spring.shell.exception.PatternMatcherException;
import com.mark.spring.shell.file.FileComponent;
import com.mark.spring.shell.model.PatternMatcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest(classes = FileComponent.class)
class ShellApplicationTests {
    @Autowired
    FileComponent fileComponent;

    @Test
    void testCurrentFile() {
        File file = new File("./");
        FileComponent fileComponent = new FileComponent();
        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(file.list())));
    }

    @Test
    void moveTest() {
        File file = new File("./src");
        FileComponent fileComponent = new FileComponent();
        fileComponent.move("src");
        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(file.list())));
    }

    @Test
    void moveBeforeTest() {
        File file = new File("./");
        FileComponent fileComponent = new FileComponent();
        fileComponent.move("src");
        fileComponent.before();
        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(file.list())));
    }

    @Test
    void moveAfterTest() {
        File file = new File("./src");
        FileComponent fileComponent = new FileComponent();
        fileComponent.move("src");
        fileComponent.after();
        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(file.list())));

    }

    @Test
    void moveBeforeAndAfterTest() {
        File currentFile = new File("./");
        File srcFile = new File("./src");

        FileComponent fileComponent = new FileComponent();
        fileComponent.move("src");
        fileComponent.move("../");
        fileComponent.move("src");

        fileComponent.before(); // .

        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(currentFile.list())));

        fileComponent.after(); // ./src

        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(srcFile.list())));

        fileComponent.before(); // .

        Assertions.assertThat(fileComponent.list())
                .isEqualTo(List.of(Objects.requireNonNull(currentFile.list())));
    }

    @Test
    void testFilePathConvert() throws PatternMatcherException {
        System.out.println(convertPath("/test/.ababab/."));
    }

    private String convertPath(String path) throws PatternMatcherException {
        PatternMatcher beforeFileMatcher = new PatternMatcher("\\/[.]{2,}");
        PatternMatcher currentFileMatcher = new PatternMatcher("/[.]{1}([^\\w]|/)");
        StringBuilder builder = new StringBuilder(path);
        if (beforeFileMatcher.test(path)) {
            int start = beforeFileMatcher.getStart();
            int end = beforeFileMatcher.getEnd();
            builder.replace(start, end, "");
            int beforeFileIndex = builder.lastIndexOf("/", start-1);
            builder.replace(beforeFileIndex, start, "");
        }
        if (currentFileMatcher.test(path)) {
            int start = currentFileMatcher.getStart();
            int end = currentFileMatcher.getEnd();
            builder.replace(start,end,"");
        }
        return builder.toString();
    }

}
