/**
 * ProcessState.java
 * Written by: Augusto M.P (40208080)
 * For COMP 371, Assignment #1
 */

package models;

public enum ProcessState {
    New, // The process has been created
    Ready, // The process is ready to be executed
    Running, // The process is currently being executed
    Waiting, // The process is waiting for an IO device (either waiting for access or waiting for IO to finish)
    Terminated // The process has finished executing
}
