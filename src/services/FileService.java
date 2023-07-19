/**
 * FileService.java
 * Written by: Augusto M.P (40208080)
 * For COMP 346, Assignment #1
 */

package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("ResultOfMethodCallIgnored")
/**
 * A service class for reading and writing files. Not specific to this project.
 */
public class FileService {
    /**
     * Reads a file and returns an ArrayList of Strings, where each String is a line from the file.
     * @param path The path to the file to be read.
     * @return An ArrayList of Strings, where each String is a line from the file.
     */
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

    /**
     * Creates a FileWriter for the file at the given path. Convenience method for creating a FileWriter.
     * @param path The path to the file to be written to.
     * @return A FileWriter for the file at the given path.
     */
    public static FileWriter startWritingFile(String path) {
        final var currentFile = new File(path);

        try {
            currentFile.createNewFile();

            return new FileWriter(currentFile);
        } catch (IOException e) {
            System.out.printf("An error occurred file %s could not be created.\n", currentFile.getAbsolutePath());
            return null;
        }
    }

    /**
     * Writes a piece of text to a file. Convenience method for writing to a file.
     * @param fileWriter The FileWriter to be used to write to the file.
     * @param text The text to be written to the file.
     */
    public static void writeToFile(FileWriter fileWriter, String text) {
        try {
            fileWriter.write(text);
        } catch (IOException e) {
            System.out.printf("An error occurred and the line %s could not be written to the file.\n", text);
        }
    }

    /**
     * Closes a FileWriter. Convenience method for closing a FileWriter.
     * @param fileWriter
     */
    public static void closeFile(FileWriter fileWriter) {
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.print("An error occurred and the file could not be closed.\n");
        }
    }
}
