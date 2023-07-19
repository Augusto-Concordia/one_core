package models;

import java.util.ArrayDeque;

public class IODevice {
    public final static int IO_PROCESSING_TIME = 5;

    private final int id;

    private int processingTime;

    public int getId() {
        return id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public IODevice() {
        this(0, 0);
    }

    public IODevice(int id) {
        this(id, 0);
    }

    public IODevice(int id, int processingTime) {
        this.id = id;
        this.processingTime = 0;
    }

    public void decrementProcessingTime() {
        this.processingTime--;
    }

    public boolean isBusy() {
        return this.processingTime > 0;
    }
}
