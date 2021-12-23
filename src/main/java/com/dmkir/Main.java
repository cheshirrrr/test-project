package com.dmkir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("done!");

        for (int i = 0; i < 600; i++) {
            Files.copy(Paths.get("test/file1"), Paths.get("test", "filecopy" + i));
        }
    }
}
