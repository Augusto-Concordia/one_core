/**
 * Register.java
 * Written by: Augusto M.P (40208080)
 * For COMP 346, Assignment #1
 */

package models;

/**
 * A class representing a register.
 */
public class Register {
    private int value; // The value of the register

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    /**
     * Creates a new Register with value 0.
     */
    public Register() {
        this(0);
    }

    /**
     * Creates a new Register with the given value.
     * @param value The value of the register.
     */
    public Register(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Register { ");
        sb.append(value);
        sb.append(" }");

        return sb.toString();
    }

    /**
     * Randomizes the value of the register (as if it was being used)
     */
    public void randomizeValue() {
        this.value = (int) (Math.random() * 100000);
    }
}
