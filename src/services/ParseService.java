/**
 * ParseService.java
 * Written by: Augusto M.P (40208080)
 * For COMP 346, Assignment #1
 */

package services;

import models.Process;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A class containing methods to parse data. In a real-world project, this would be a generic class, but for the sake of this assignment, it's not.
 */
public class ParseService {

    /**
     * Parses the given lines into an array of processes.
     * @param lines The lines to parse.
     * @return An array of processes.
     */
    public static Process[] parseLinesIntoProcesses(ArrayList<String> lines) {
        // The pattern for a line is: <process id>, <number of instructions>, [<io requests>], [<io devices requested>]
        final var linePattern = Pattern.compile("([0-9]), ([0-9]+), \\[(.+)*\\] \\[(.+)*\\]", Pattern.CASE_INSENSITIVE);

        // Subtract 2 because we're skipping the first line
        final var processes = new Process[lines.size() - 1];

        // Skip the first line, which are the headers for each column
        for (int i = 1; i < lines.size(); i++) {
            final var line = lines.get(i);
            final Process process;

            final var matcher = linePattern.matcher(line);

            // Even if it's not strictly necessary, we check if the line matches the pattern (i.e. if it's a valid line)
            if (!matcher.find()) continue;

            process = new Process(Integer.parseInt(matcher.group(1)));
            process.setInstructionCount(Integer.parseInt(matcher.group(2)));

            // If the third group is null, then we don't have any IO operations
            if (matcher.group(3) != null) {
                final var ioRequestStrings = matcher.group(3).split(",");
                final var ioRequests = new int[ioRequestStrings.length];

                final var ioDeviceRequestedStrings = matcher.group(4).split(",");
                final var ioDevicesRequested = new int[ioDeviceRequestedStrings.length];

                // We can solely take the length of the third group because it will always be the same as the second group (as said by the assignment)
                for (int j = 0; j < ioRequestStrings.length; j++) {
                    ioRequests[j] = Integer.parseInt(ioRequestStrings[j]);

                    // We subtract 1 from the ioRequest because the assignment says that the first instruction is 1, but we want it to be 0-based
                    ioDevicesRequested[j] = Integer.parseInt(ioDeviceRequestedStrings[j]) - 1;
                }

                process.setIoRequests(ioRequests, ioDevicesRequested);
            }

            // We subtract 1 from the process array, because we ignore the first line (which is the headers)
            processes[i - 1] = process;
        }

        return processes;
    }
}
