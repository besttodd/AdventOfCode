package besttod.com;

//Day 8: Part 1 - The boot code is represented as a text file with one instruction per line of text.
//                Each instruction consists of an operation (acc, jmp, or nop) and an argument (a signed number like +4 or -20).
//                     - acc increases or decreases a single global value called the accumulator by the value given in the argument.
//                     - jmp jumps to a new instruction relative to itself. The next instruction to execute is found using the argument as an offset from the jmp instruction.
//                     - nop stands for No OPeration - it does nothing. The instruction immediately below it is executed next.
//                Run your copy of the boot code. Immediately before any instruction is executed a second time, what value is in the accumulator?
//Correct answer: 1614
//       Part 2 - Somewhere in the program, either a jmp is supposed to be a nop, or a nop is supposed to be a jmp. (No acc instructions were harmed in the corruption of this boot code.)
//                The program is supposed to terminate by attempting to execute an instruction immediately after the last instruction in the file.
//                By changing exactly one jmp or nop, you can repair the boot code and make it terminate correctly.
//                Fix the program so that it terminates normally by changing exactly one jmp (to nop) or nop (to jmp).
//                What is the value of the accumulator after the program terminates?
//Incorrect answers: 645
//Correct answer: 1260


import java.util.*;

public class Day8 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day8_input.txt";

    private List<Integer> possibleErrors = new ArrayList<>();

    public Day8() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        List<Instruction> bootCode = new ArrayList<>();
        for (String line : input) {
            bootCode.add(new Instruction(line));
        }

        List<Integer> changes = new ArrayList<>();
        changes.add(0);
        possibleErrors.add(0);
        int lastChange = 0;
        int count = 0;

        while (checkForLoop(bootCode)) {
            if (count > 0) {
                bootCode.get(lastChange).swapOperation();
            } else {
                possibleErrors.remove(0);
            }

            for (int i : changes) {
                if (possibleErrors.contains(i)) {
                    possibleErrors.remove((Integer) i);
                }
            }

            lastChange = possibleErrors.get(0);

            switch (bootCode.get(lastChange).getOperation()) {
                case "nop":
                    bootCode.get(lastChange).swapOperation();
                    System.out.println("\nNOP CHANGED at line: " + lastChange + "=====================================================");
                    possibleErrors.remove(0);
                    changes.add(lastChange);
                    break;
                case "jmp":
                    bootCode.get(lastChange).swapOperation();
                    System.out.println("\nJMP CHANGED  at line: " + lastChange + "======================================================\n");
                    possibleErrors.remove(0);
                    changes.add(lastChange);
                    break;
            }
            count++;
        }
    }

    private boolean checkForLoop(List<Instruction> code) {
        int accumulator = 0;
        int lineNum = 0;
        boolean[] repeat = new boolean[code.size()];
        Arrays.fill(repeat, false);
        try {
            while (!repeat[lineNum]) {
                repeat[lineNum] = true;
                switch (code.get(lineNum).getOperation()) {
                    case "nop":
                        possibleErrors.add(lineNum);
                        System.out.println("Line: " + lineNum + " No Operation.");
                        break;
                    case "acc":
                        accumulator += code.get(lineNum).getArgument();
                        System.out.println("Line: " + lineNum + " Accumulator adjusted: " + accumulator);
                        break;
                    case "jmp":
                        int prev = lineNum;
                        possibleErrors.add(lineNum);
                        lineNum += code.get(lineNum).getArgument() - 1;
                        System.out.println("Line: " + prev + " Jump to line#: " + (lineNum + 1));
                        break;
                    default:
                        System.out.println("Error: Unknown operation.");
                }
                lineNum++;
            }
            System.out.println("\nInfinite loop in Boot Code.\nAccumulator: " + accumulator + " Line: " + lineNum);
            return true;
        } catch (Exception e) {
            System.out.println("\nBoot Code executed successfully.\nAccumulator: " + accumulator);
            return false;
        }
    }
}
