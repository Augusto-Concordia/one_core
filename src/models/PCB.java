package models;

public class PCB {
    private int id;

    private ProcessState state;
    private int counter;

    private int busyTime;
    private int aliveTime;

    private Register[] registers;
    private IODevice[] ioDevices;
}
