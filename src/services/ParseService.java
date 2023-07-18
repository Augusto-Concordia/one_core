package services;

import models.Process;

import java.util.ArrayList;
import java.util.regex.Pattern;

// In a real project, this class would be generic and would be able to parse any type of object
public class ParseService {
    public static Process[] parseLinesIntoProcesses(ArrayList<String> lines) {
        final var linePattern = Pattern.compile("([0-9]), ([0-9]+), \\[(.+)*\\] \\[(.+)*\\]", Pattern.CASE_INSENSITIVE);

        final var processes = new Process[lines.size() - 1];

        // Skip the first line, which are the headers for each column
        for (int i = 1; i < lines.size(); i++) {
            final var line = lines.get(i);
            final var process = new Process();

            final var matcher = linePattern.matcher(line);

            // Even if it's not strictly necessary, we check if the line matches the pattern (i.e. if it's a valid line)
            if (matcher.find()) {
                process.setId(Integer.parseInt(matcher.group(1)));
                process.setInstructionCount(Integer.parseInt(matcher.group(2)));

                // If the third group is null, then we don't have any IO operations
                if (matcher.group(3) == null)
                    continue;

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

                process.setIoRequests(ioRequests);
                process.setIoDevicesRequested(ioDevicesRequested);

                processes[i - 1] = process;
            }
        }

        return processes;
    }
}
