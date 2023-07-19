/**
 * Process.java
 * Written by: Augusto M.P (40208080)
 * For COMP 346, Assignment #1
 */

package models;

import java.util.Hashtable;

/**
 * A class representing a process.
 */
public class Process {
    private final int id; // The id of the process

    private int instructionCount; // The number of instructions the process has

    private final Hashtable<Integer, Integer> ioRequests = new Hashtable<>(); // The IO requests of the process. The key is the instruction number, the value is the IO device requested

    public int getId() {
        return id;
    }

    public int getInstructionCount() {
        return instructionCount;
    }

    public void setInstructionCount(int instructionCount) {
        this.instructionCount = instructionCount;
    }

    public Hashtable<Integer, Integer> getIoRequests() {
        return new Hashtable<>(ioRequests);
    }

    public void setIoRequests(int[] ioRequests, int[] ioDevicesRequested) {
        for (int i = 0; i < ioRequests.length; i++) {
            this.ioRequests.put(ioRequests[i], ioDevicesRequested[i]);
        }
    }

    /**
     * Creates a new Process with id -1, instruction count 0, and no IO requests.
     */
    public Process() {
        this(-1, 0, new int[0], new int[0]);
    }

    /**
     * Creates a new Process with the given id, instruction count 0, and no IO requests.
     * @param id The id of the process.
     */
    public Process(int id) {
        this(id, 0, new int[0], new int[0]);
    }

    /**
     * Creates a new Process with the given id, instruction count, and IO requests.
     * @param id The id of the process.
     * @param instructionCount The number of instructions the process has.
     * @param ioRequests The IO requests of the process.
     * @param ioDevicesRequested The IO devices requested by the process.
     */
    public Process(int id, int instructionCount, int[] ioRequests, int[] ioDevicesRequested) {
        this.id = id;
        this.instructionCount = instructionCount;

        this.setIoRequests(ioRequests, ioDevicesRequested);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Process { ");
        sb.append("id: ").append(id);
        sb.append(", instructionCount: ").append(instructionCount);
        sb.append(", ioRequests: ").append(ioRequests);
        sb.append(" }");

        return sb.toString();
    }
}
