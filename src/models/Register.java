package models;

public class Register {
    private int value;

    public Register() {
        this(0);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public Register(int value) {
        this.value = value;
    }
}
