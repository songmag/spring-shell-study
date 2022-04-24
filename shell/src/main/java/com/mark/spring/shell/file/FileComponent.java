package com.mark.spring.shell.file;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class FileComponent {
    private File currentFile;
    private Deque<File> fileHistories = new LinkedList<>();

    public FileComponent() {
        Path path = Paths.get(convertPath(new File("./").getAbsolutePath()));
        currentFile = path.toFile();
        this.fileHistories.push(this.currentFile);
    }

    /**
     * cd command
     *
     * @param path
     */
    public void move(String path) {
        File nextFile = checkFile(currentFile.getPath() + "/" + path + "/");
        this.fileHistories.push(currentFile);
        this.currentFile = nextFile;
    }

    public List<String> list() {
        return List.of(Objects.requireNonNull(this.currentFile.list()));
    }

    public File checkFile(String path) {
        File moveFile = new File(convertPath(path));
        if (!moveFile.exists()) {
            throw new NullPointerException("파일이 존재하지 않습니다");
        }
        return moveFile;
    }

    /**
     * + command
     */
    public void after() {
        File last = this.currentFile;
        this.currentFile = this.fileHistories.pop();
        this.fileHistories.offer(last);
    }

    /**
     * - command
     */
    public void before() {
        File first = this.currentFile;
        this.currentFile = this.fileHistories.poll();
        this.fileHistories.push(first);
    }

    private String convertPath(String path) {
        if (path.contains(".")) {
            int last = path.lastIndexOf(".");
            if (last == path.length() - 1) {
                StringBuilder builder = new StringBuilder(path);
                builder.replace(last, last + 1, "");
                return builder.toString();
            }
        }
        if (path.contains("./")) {
            int last = path.lastIndexOf("./");
            StringBuilder builder = new StringBuilder(path);
            builder.replace(last, last + 1, "");
            return builder.toString();
        }
        if (path.contains("../")) {
            int last = path.lastIndexOf("../");
            int lastPath = path.lastIndexOf("/", last - 1);
            StringBuilder builder = new StringBuilder(path);
            builder.replace(lastPath + 1, last, "");
            return builder.toString();
        }
        return path;
    }

}
