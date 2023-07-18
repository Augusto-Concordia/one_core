package models;

import java.util.Arrays;

public class Process {
    private int id;

    private int instructionCount;

    private int[] ioRequests;
    private int[] ioDevicesRequested;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstructionCount() {
        return instructionCount;
    }

    public void setInstructionCount(int instructionCount) {
        this.instructionCount = instructionCount;
    }

    public int[] getIoRequests() {
        return ioRequests;
    }

    public void setIoRequests(int[] ioRequests) {
        this.ioRequests = ioRequests;
    }

    public int[] getIoDevicesRequested() {
        return ioDevicesRequested;
    }

    public void setIoDevicesRequested(int[] ioDevicesRequested) {
        this.ioDevicesRequested = ioDevicesRequested;
    }

    public Process() {
        this(0, 0, new int[0], new int[0]);
    }

    public Process(int id, int instructionCount, int[] ioRequests, int[] ioDevicesRequested) {
        this.id = id;
        this.instructionCount = instructionCount;
        this.ioRequests = ioRequests;
        this.ioDevicesRequested = ioDevicesRequested;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Process { ");
        sb.append("id: ").append(id);
        sb.append(", instructionCount: ").append(instructionCount);
        sb.append(", ioRequests: ").append(Arrays.toString(ioRequests));
        sb.append(", ioDevicesRequested: ").append(Arrays.toString(ioDevicesRequested));
        sb.append(" }");

        return sb.toString();
    }
}
