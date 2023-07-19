/**
 * PCB.java
 * Written by: Augusto M.P (40208080)
 * For COMP 371, Assignment #1
 */

package models;

import java.util.Arrays;

/**
 * A class representing a Process Control Block.
 */
public class PCB {
    private final int id; // The id of the PCB is the same as the id of the process it represents
    private final Process process; // The process this PCB represents

    private ProcessState state; // The state of the PCB
    private int counter; // The program counter of the PCB

    private int aliveTime; // For accounting purposes, keeps track of how many operations has been done to the process (instructions + io requests)

    private Register[] registers; // The registers of the PCB
    private IODevice ioDevice; // The IO device of the PCB. Null if the PCB is not processing an IO request

    public int getId() {
        return id;
    }

    public Process getProcess() {
        return process;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getCounter() {
        return counter;
    }


    public int getAliveTime() {
        return aliveTime;
    }

    public Register[] getRegisters() {
        return registers;
    }

    public void setRegisters(Register[] registers) {
        this.registers = registers;
    }

    public IODevice getIoDevice() {
        return ioDevice;
    }

    public void setIoDevice(IODevice ioDevice) {
        this.ioDevice = ioDevice;
    }

    /**
     * Creates a "blank" PCB with an id of -1.
     */
    public PCB() {
        this(-1, null, ProcessState.New, 0, 0, new Register[0], null);
    }

    /**
     * Creates a PCB with the given id.
     * @param id The id of the PCB.
     */
    public PCB(int id) {
        this(id, null, ProcessState.New, 0, 0, new Register[0], null);
    }

    /**
     * Creates a PCB from a Process.
     * @param process The process to create the PCB from.
     * @param registers The registers to initialize the PCB with.
     */
    public PCB(Process process, Register[] registers) {
        this(process.getId(), process, ProcessState.New, 0, 0, registers, null);
    }

    /**
     * Creates a PCB with the given parameters.
     * @param id The id of the PCB.
     * @param process The process to create the PCB from.
     * @param state The state of the PCB.
     * @param counter The program counter of the PCB.
     * @param aliveTime The alive time of the PCB.
     * @param registers The registers of the PCB.
     * @param ioDevice The IO device of the PCB.
     */
    public PCB(int id, Process process, ProcessState state, int counter, int aliveTime, Register[] registers, IODevice ioDevice) {
        this.id = id;
        this.process = process;
        this.state = state;
        this.counter = counter;
        this.aliveTime = aliveTime;
        this.registers = registers;
        this.ioDevice = ioDevice;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PCB { ");
        sb.append("id: ").append(id);
        sb.append(", state: ").append(state);
        sb.append(", counter: ").append(counter);
        sb.append(", aliveTime: ").append(aliveTime);
        sb.append(", registers: ").append(Arrays.toString(registers));
        sb.append(", ioDevice: ").append(ioDevice);
        sb.append(" }");
        return sb.toString();
    }

    /**
     * Simulates execution of an instruction on the PCB.
     */
    public void executeInstruction() {
        this.counter++;
        this.aliveTime++;

        for (var register : this.registers) {
            register.randomizeValue();
        }
    }

    /**
     * Simulates execution of an IO request on the PCB.
     */
    public void executeIoRequestTick() {
        if (this.ioDevice == null) return;

        this.ioDevice.executeIoTick();

        this.aliveTime++;
    }

    /**
     * Checks if the PCB is currently processing an IO request.
     * @return True if the PCB is currently processing an IO request, false otherwise.
     */
    public boolean isProcessingIoRequest() {
        return this.ioDevice != null;
    }

    /**
     * Checks if the PCB has completed an IO request.
     * @return True if the PCB has completed an IO request, false otherwise.
     */
    public boolean hasCompletedIoRequest() {
        return this.ioDevice != null && this.ioDevice.isIdle();
    }

    /**
     * Checks if the process in this PCB has finished all of its instructions.
     * @return True if the process in this PCB has finished all of its instructions, false otherwise.
     */
    public boolean hasFinishedInstructions() {
        return this.counter >= process.getInstructionCount();
    }
}
