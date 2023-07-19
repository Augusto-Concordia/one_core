/**
 * Main.java
 * Written by: Augusto M.P (40208080)
 * For COMP 346, Assignment #1
 */

import models.IODevice;
import models.PCB;
import models.ProcessState;
import models.Register;
import services.FileService;
import services.ParseService;

import java.io.FileWriter;
import java.util.*;

/**
 * The main class for the program. Contains the main method.
 */
@SuppressWarnings("unchecked")
public class Main {
    public static void main(String[] args) {
        // Gets the lines from the input file (given by the 1st argument)
        final var lines = FileService.readFile(args[0]);

        // An error occurred when reading the file
        if (lines == null) {
            return;
        }

        // Parse the input file into processes
        final var processes = ParseService.parseLinesIntoProcesses(lines);

        // Creates a file writer for the output file (given by the 2nd argument)
        final var outputWriter = FileService.startWritingFile(args[1]);

        // Global time stamp
        int timeStamp = 0;

        // Process Control Block queues
        final var newPcbs = new ArrayDeque<PCB>();
        final var readyPcbs = new ArrayDeque<PCB>();
        final var runningPcbs = new ArrayDeque<PCB>();
        final var waitingPcbs = new ArrayDeque[] {new ArrayDeque<PCB>(), new ArrayDeque<PCB>()};
        final var terminatedPcbs = new ArrayDeque<PCB>();

        // IO Devices
        final var ioDevices = new IODevice[]{
                new IODevice(1),
                new IODevice(2),
        };

        // Initial fill of new processes
        for (models.Process process : processes) {
            newPcbs.push(new PCB(process, new Register[]{new Register(), new Register()}));
        }

        // Control loop
        while (terminatedPcbs.size() < processes.length) {
            PCB currentPcb = null;

            // Admissions
            while (!newPcbs.isEmpty()) {
                final var pcb = newPcbs.pop();

                pcb.setState(ProcessState.Ready);
                readyPcbs.push(pcb);
            }

            // IO Device 1
            processIo(ioDevices[0], waitingPcbs[0], readyPcbs);

            // IO Device 2
            processIo(ioDevices[1], waitingPcbs[1], readyPcbs);

            // If we're processing a PCB, get it
            if (!runningPcbs.isEmpty()) {
                currentPcb = runningPcbs.pop();
            }
            // Finally, get the first element in the ready queue since we're not processing one
            else if (!readyPcbs.isEmpty()) {
                currentPcb = readyPcbs.pop();
                currentPcb.setState(ProcessState.Running);
            }

            // If the current process has an IO request for its next instruction, add it to the waiting queue for the appropriate IO device
            if (currentPcb != null && currentPcb.getState() == ProcessState.Running) {
                final var possibleIoRequest = currentPcb.getProcess().getIoRequests().get(currentPcb.getCounter() + 1);

                // If there is an IO request, add it to the appropriate waiting queue
                if (possibleIoRequest != null) {
                    currentPcb.setState(ProcessState.Waiting);

                    waitingPcbs[possibleIoRequest].add(currentPcb);

                    currentPcb = null;
                }
            }

            // The current PCB is ready to be processed
            if (currentPcb != null)
                currentPcb.executeInstruction();

            // If the current PCB has finished its instructions, terminate it
            if (currentPcb != null && currentPcb.hasFinishedInstructions()) {
                currentPcb.setState(ProcessState.Terminated);
                currentPcb.setRegisters(new Register[0]);
                terminatedPcbs.add(currentPcb);
            }
            // If the current PCB hasn't had all of its allocated time on the CPU (2 time units), put it with the running PCBs
            else if (currentPcb != null && currentPcb.getCounter() % 2 != 0) {
                currentPcb.setState(ProcessState.Running);
                runningPcbs.add(currentPcb);
            }
            // Otherwise, if the current PCB has had all of its time on the CPU (2 time units), put it with the ready PCBs
            else if (currentPcb != null && currentPcb.getCounter() % 2 == 0) {
                currentPcb.setState(ProcessState.Ready);
                readyPcbs.add(currentPcb);
            }

            printCpuState(timeStamp, readyPcbs, runningPcbs, waitingPcbs[0], waitingPcbs[1], terminatedPcbs, outputWriter);
            timeStamp++;
        }

        if (outputWriter != null) FileService.closeFile(outputWriter);
    }

    /**
     * Processes the IO requests for a given IO device.
     * @param ioDevice The IO device to process the IO requests for.
     * @param waitingPcbs The queue of PCBs waiting for this IO device to be available (including PCBs that have a current IO request).
     * @param readyPcbs The queue of PCBs so that PCBs that have completed IO can be transferred out.
     */
    private static void processIo(IODevice ioDevice, Deque<PCB> waitingPcbs, Deque<PCB> readyPcbs) {
        int ioOneSize = waitingPcbs.size();

        // Goes through all the waiting PCBs for IO Device 1
        for (int i = 0; i < ioOneSize; i++) {
            final var pcb = waitingPcbs.pop();

            // If the PCB is processing an IO request, it might be done
            if (pcb.isProcessingIoRequest()) {
                pcb.executeIoRequestTick();

                // If it's done, move the PCB to the ready queue
                if (pcb.hasCompletedIoRequest()) {
                    pcb.setIoDevice(null);
                    pcb.setState(ProcessState.Ready);
                    pcb.executeInstruction(); // Execute the instruction that is the IO request

                    readyPcbs.add(pcb);
                    continue;
                }

            }
            // Or maybe the PCB is waiting for this IO device to be available
            else if (ioDevice.isIdle()) {
                ioDevice.startProcessing();

                pcb.setState(ProcessState.Waiting);
                pcb.setIoDevice(ioDevice);
            }

            // Add it back to the queue in the same order for the next iteration
            waitingPcbs.add(pcb);
        }
    }

    /**
     * Prints the CPU state to the console and to the output file (if it exists).
     * @param timeStamp The current simulated time stamp.
     * @param readyPcbs The queue of PCBs that are ready to be processed.
     * @param runningPcbs The queue of PCBs that are currently being processed.
     * @param waitingPcbsIoOne The queue of PCBs that are waiting for IO Device 1 to be available.
     * @param waitingPcbsIoTwo The queue of PCBs that are waiting for IO Device 2 to be available.
     * @param terminatedPcbs The queue of PCBs that have finished executing.
     * @param outputWriter The FileWriter for the output file, if it exists.
     */
    private static void printCpuState(int timeStamp, Collection<PCB> readyPcbs, Collection<PCB> runningPcbs, Collection<PCB> waitingPcbsIoOne, Collection<PCB> waitingPcbsIoTwo, Collection<PCB> terminatedPcbs, FileWriter outputWriter) {
        StringBuilder sb = new StringBuilder("CPU State (@ ").append(timeStamp).append(" time units) \n");

        // Ready PCBs
        sb.append("  Ready PCBs\n");
        for (var pcb : readyPcbs) {
            sb.append("    ").append(pcb).append("\n");
        }

        // Running PCBs
        sb.append("  Running PCBs\n");
        for (var pcb : runningPcbs) {
            sb.append("    ").append(pcb).append("\n");
        }

        // Waiting PCBs for device 1
        sb.append("  Waiting PCBs (IO Device #1)\n");
        for (var pcb : waitingPcbsIoOne) {
            sb.append("    ").append(pcb).append("\n");
        }

        // Waiting PCBs for device 2
        sb.append("  Waiting PCBs (IO Device #2)\n");
        for (var pcb : waitingPcbsIoTwo) {
            sb.append("    ").append(pcb).append("\n");
        }

        // Terminated PCBs
        sb.append("  Terminated PCBs\n");
        for (var pcb : terminatedPcbs) {
            sb.append("    ").append(pcb).append("\n");
        }

        System.out.println(sb);

        if (outputWriter != null) {
            FileService.writeToFile(outputWriter, sb + "\n");
        }
    }
}