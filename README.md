# one_core

COMP 346 - Assignment 1

## Getting Started

This is a simple project simulating a one core processor dealing with different processes and IO devices, written in Java (Java 16).

## How to run

Supply 2 arguments to the program: the first is the path to the input file, the second is the path to the output file. The input file should be in the following format:

```text
<process id>, <number of instructions>, [<io requests>], [<io devices requested>]
0, 10, [1, 5, 8], [1, 2, 1]
[...]
```

The number of IO requests and IO devices requested (maximum 2 different devices) must be the same, and the number of instructions must be greater than the number of IO requests.

The output file will be in the following format:

```text
CPU State (@ 11 time units) 
  Ready PCBs
    PCB { id: 2, state: Ready, counter: 0, aliveTime: 0, registers: [Register { 0 }, Register { 0 }], ioDevice: null }
    PCB { id: 3, ... }
  Running PCBs
    PCB { id: 1, state: Running, counter: 7, aliveTime: 10, registers: [Register { 99 }, Register { 30 }], ioDevice: null }
  Waiting PCBs (IO Device #1)
  Waiting PCBs (IO Device #2)
  Terminated PCBs
```

Please take a look at the sample input and output files in the root folder.