package models;

import java.util.Dictionary;
import java.util.Hashtable;

public class Process {
    private int id;

    private int instructionCount;

    private final Hashtable<Integer, Integer> ioRequests = new Hashtable<>();

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

    public Process() {
        this(-1, 0, new int[0], new int[0]);
    }

    public Process(int id) {
        this(id, 0, new int[0], new int[0]);
    }

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
