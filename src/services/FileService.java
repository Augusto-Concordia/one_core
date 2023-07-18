package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileService {
    public static ArrayList<String> readFile(String path) {
        final var lines = new ArrayList<String>();

        final var currentFile = new File(path);

        try {
            final var fileReader = new Scanner(currentFile);

            while (fileReader.hasNextLine()) {
                lines.add(fileReader.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.printf("An error occurred and file %s could not be found.\n", currentFile.getAbsolutePath());
            return null;
        }

        return lines;
    }
}
