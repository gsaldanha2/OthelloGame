package com.game.othello;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Gregory on 2/4/2016.
 */
public class WriteFile {

    private String path;

    public WriteFile(String filepath) {
        path = filepath;
    }

    public void writeToFile(String line, boolean appendToFile) throws IOException {
        FileWriter write = new FileWriter(path, appendToFile);
        PrintWriter printLine = new PrintWriter(write);
        printLine.printf("%s" + "%n", line);
        printLine.close();
    }
}
