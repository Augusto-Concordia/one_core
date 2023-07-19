import models.IODevice;
import models.PCB;
import models.ProcessState;
import models.Register;
import services.FileService;
import services.ParseService;

import java.util.ArrayDeque;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        final var lines = FileService.readFile("input.txt");

        // An error occurred when reading the file
        if (lines == null) {
            return;
        }

        // Parse the input file into processes
        final var processes = ParseService.parseLinesIntoProcesses(lines);

        int timeStamp = 0;

        // Process Control Block queues
        final var newPcbs = new ArrayDeque<PCB>();
        final var readyPcbs = new ArrayDeque<PCB>();
        final var waitingPcbs = new ArrayDeque<PCB>();
        final var terminatedPcbs = new ArrayDeque<PCB>();

        // Registers
        final var registers = new Register[]{
                new Register(),
                new Register(),
        };

        // IO Devices
        final var ioDevices = new IODevice[]{
                new IODevice(0),
                new IODevice(1),
        };

        // Initial fill of new processes
        for (models.Process process : processes) {
            newPcbs.push(new PCB(process));
        }

        // Control loop
        while (terminatedPcbs.size() < processes.length) {
            PCB currentPcb = null;

            // Admissions
            while (!newPcbs.isEmpty()){
                final var pcb = newPcbs.pop();

                pcb.setState(ProcessState.Ready);
                readyPcbs.push(pcb);
            }

            // Get the first element in the ready queue if we're not processing one
            if (!readyPcbs.isEmpty()) {
                currentPcb = readyPcbs.pop();
                currentPcb.setState(ProcessState.Running);
            }
            // If there is an ongoing IO request, process it
            else if (!waitingPcbs.isEmpty() && waitingPcbs.peek().hasCompletedIoRequest())
            {
                waitingPcbs.peek().executeIoRequest();
            }
            // Otherwise, the IO request has finished being processed
            else if (!waitingPcbs.isEmpty() && !waitingPcbs.peek().hasCompletedIoRequest()) {
                currentPcb = waitingPcbs.pop();

                currentPcb.setIoDevice(null);
                currentPcb.executeInstruction();
                currentPcb.setState(ProcessState.Running);
            }

            // If the current process has an IO request, process it accordingly
            if (currentPcb != null && currentPcb.getState() == ProcessState.Running) {
                final var possibleIoRequest = currentPcb.getProcess().getIoRequests().get(currentPcb.getCounter());

                // If there is an IO request, process it accordingly
                if (possibleIoRequest != null)
                {
                    var currentIoDevice = ioDevices[possibleIoRequest];
                    currentIoDevice.setProcessingTime(IODevice.IO_PROCESSING_TIME);

                    currentPcb.setState(ProcessState.Waiting);
                    currentPcb.setIoDevice(currentIoDevice);

                    waitingPcbs.add(currentPcb);
                    currentPcb = null;
                }
            }

            // The current PCB is ready to be processed
            if (currentPcb != null)
                currentPcb.executeInstruction();

            if (currentPcb != null && currentPcb.hasFinishedInstructions())
            {
                currentPcb.setState(ProcessState.Terminated);
                terminatedPcbs.add(currentPcb);
            }
            else if (currentPcb != null)
            {
                currentPcb.setState(ProcessState.Ready);
                readyPcbs.add(currentPcb);
            }

            printCpuState(timeStamp, readyPcbs, terminatedPcbs);
            timeStamp++;
        }
    }

    private static void printCpuState(int timeStamp, Collection<PCB> readyPcbs, Collection<PCB> terminatedPcbs) {
        StringBuilder sb = new StringBuilder("CPU State (@ ").append(timeStamp).append(" units) \n");

        // Ready PCBs
        sb.append("  Ready PCBs\n");

        for (var pcb : readyPcbs) {
            sb.append("    ").append(pcb).append("\n");
        }

        // Running PCBs
        sb.append("  Terminated PCBs\n");

        for (var pcb : terminatedPcbs) {
            sb.append("    ").append(pcb).append("\n");
        }

        System.out.println(sb);
    }
}