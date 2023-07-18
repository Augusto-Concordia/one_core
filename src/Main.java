import services.FileService;
import services.ParseService;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final var lines = FileService.readFile("input.txt");

        // An error occurred when reading the file
        if (lines == null) {
            return;
        }

        System.out.println(Arrays.toString(ParseService.parseLinesIntoProcesses(lines)));
    }
}