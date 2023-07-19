/**
 * IODevice.java
 * Written by: Augusto M.P (40208080)
 * For COMP 346, Assignment #1
 */

package models;

/**
 * A class representing an IO Device.
 */
public class IODevice {
    public final static int IO_PROCESSING_TIME = 5; // The time it takes for an IO device to process an IO request

    private final int id; // The id of the IO device
    private int processingTime; // The time left for the IO device to finish processing an IO request

    public int getId() {
        return id;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    /**
     * Creates a new IODevice with id 0 and processing time 0.
     */
    public IODevice() {
        this(0, 0);
    }

    /**
     * Creates a new IODevice with the given id and processing time 0.
     * @param id The id of the IO device.
     */
    public IODevice(int id) {
        this(id, 0);
    }

    /**
     * Creates a new IODevice with the given id and processing time.
     * @param id The id of the IO device.
     * @param processingTime The processing time of the IO device.
     */
    public IODevice(int id, int processingTime) {
        this.id = id;
        this.processingTime = processingTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IODevice {");
        sb.append(" id: ").append(id);
        sb.append(", processingTime: ").append(processingTime);
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Starts processing an IO request.
     */
    public void startProcessing() {
        this.processingTime = IO_PROCESSING_TIME;
    }

    /**
     * Simulates an IO tick by decrementing the processing time by 1.
     */
    public void executeIoTick() {
        this.processingTime--;
    }

    /**
     * Returns whether the IO device is idle.
     * @return True if the IO device is idle, false otherwise.
     */
    public boolean isIdle() {
        return this.processingTime <= 0;
    }
}
