import models.IODevice;
import models.PCB;
import models.ProcessState;
import models.Register;
import services.FileService;
import services.ParseService;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        final var lines = FileService.readFile("input.txt");

        // An error occurred when reading the file
        if (lines == null) {
            return;
        }

        // Parse the input file into processes
        final var processes = ParseService.parseLinesIntoProcesses(lines);

        // Process queues
        final var newPcbs = new ArrayDeque<PCB>();
        final var readyPcbs = new ArrayDeque<PCB>();
        final var waitingPcbs = new ArrayDeque<PCB>();
        final var runningPcbs = new ArrayDeque<PCB>();
        final var terminatedPcbs = new ArrayDeque<PCB>();

        // Registers
        final var registers = new Register[] {
            new Register(),
            new Register(),
        };

        // IO Devices
        final var ioDevices = new IODevice[] {
            new IODevice(),
            new IODevice(),
        };

        // Initial fill of new processes
        for (int i = 0; i < processes.length; i++) {
            newPcbs.push(new PCB(processes[i].getId()));
        }

        // Control loop
        while(terminatedPcbs.size() < processes.length) {
            // Admissions
            for (var pcb: newPcbs) {
                pcb.setState(ProcessState.Ready);
                readyPcbs.push(pcb);
            }



            printCpuState(readyPcbs);
        }
    }

    private static void printCpuState(Collection<PCB> pcbs) {
        StringBuilder sb = new StringBuilder("CPU State \n");
        sb.append("  PCBs\n");

        for (var pcb : pcbs) {
            sb.append("    ").append(pcb).append("\n");
        }

        System.out.println(sb);
    }
}