package models;

import java.util.Arrays;

public class PCB {
    private int id;

    private ProcessState state;
    private int counter;

    private int busyTime;
    private int aliveTime;

    private Register[] registers;
    private IODevice[] ioDevices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getBusyTime() {
        return busyTime;
    }

    public void setBusyTime(int busyTime) {
        this.busyTime = busyTime;
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

    public IODevice[] getIoDevices() {
        return ioDevices;
    }

    public void setIoDevices(IODevice[] ioDevices) {
        this.ioDevices = ioDevices;
    }

    public PCB() {
        this(0, ProcessState.New, 0, 0, 0, new Register[0], new IODevice[0]);
    }

    public PCB(int id) {
        this(id, ProcessState.New, 0, 0, 0, new Register[0], new IODevice[0]);
    }

    public PCB(int id, ProcessState state, int counter, int busyTime, int aliveTime, Register[] registers, IODevice[] ioDevices) {
        this.id = id;
        this.state = state;
        this.counter = counter;
        this.busyTime = busyTime;
        this.aliveTime = aliveTime;
        this.registers = registers;
        this.ioDevices = ioDevices;
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
        sb.append(", ioDevices: ").append(Arrays.toString(ioDevices));
        sb.append(" }");
        return sb.toString();
    }
}
