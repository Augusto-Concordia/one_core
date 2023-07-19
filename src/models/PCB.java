package models;

import java.util.Arrays;

public class PCB {
    private final int id;
    private final Process process;

    private ProcessState state;
    private int counter;

    private int busyTime; // For accounting purposes
    private int aliveTime;

    private Register[] registers;
    private IODevice ioDevice;

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

    public int getBusyTime() {
        return busyTime;
    }

    public int getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(int aliveTime) {
        this.aliveTime = aliveTime;
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

    public PCB() {
        this(-1, null, ProcessState.New, 0, 0, 0, new Register[0], new IODevice(-1));
    }

    public PCB(int id) {
        this(id, null, ProcessState.New, 0, 0, 0, new Register[0], new IODevice(-1));
    }

    public PCB(Process process) {
        this(process.getId(), process, ProcessState.New, 0, 0, 0, new Register[0], new IODevice(-1));
    }

    public PCB(int id, Process process, ProcessState state, int counter, int busyTime, int aliveTime, Register[] registers, IODevice ioDevice) {
        this.id = id;
        this.process = process;
        this.state = state;
        this.counter = counter;
        this.busyTime = busyTime;
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
        sb.append(", busyTime: ").append(busyTime);
        sb.append(", aliveTime: ").append(aliveTime);
        sb.append(", registers: ").append(Arrays.toString(registers));
        sb.append(", ioDevices: ").append(ioDevice);
        sb.append(" }");
        return sb.toString();
    }

    public void executeInstruction() {
        this.counter++;
        this.busyTime++;
        this.aliveTime++;
    }

    public void executeIoRequest() {
        this.ioDevice.decrementProcessingTime();

        this.busyTime++;
        this.aliveTime++;
    }

    public boolean hasCompletedIoRequest() {
        return !this.ioDevice.isBusy();
    }

    public boolean hasFinishedInstructions() {
        return this.counter >= process.getInstructionCount();
    }
}
