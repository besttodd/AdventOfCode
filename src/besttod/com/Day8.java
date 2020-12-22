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


import java.util.*;

public class Day8 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day8_input.txt";

    private boolean[] repeat;

    public Day8() {
        List<String> input = new ReadFile().readFile(FILEPATH);
        repeat = new boolean[input.size()];
        Arrays.fill(repeat, false);

        int lineNum = 0;
        int accumulator = 0;

        List<Instruction> bootCode = new ArrayList<>();
        for (String line : input) {
            bootCode.add(new Instruction(line));
        }

        while (!checkForInfinite(lineNum)) {
            repeat[lineNum] = true;
            switch (bootCode.get(lineNum).getOperation()) {
                case "nop":
                    //System.out.println("No Operation.");
                    break;
                case "acc":
                    accumulator += bootCode.get(lineNum).getArgument();
                    //System.out.println("Accumulator adjusted: "+accumulator);
                    break;
                case "jmp":
                    lineNum += bootCode.get(lineNum).getArgument() - 1;
                    //System.out.println("Jump to line#: "+lineNum);
                    break;
                default:
                    System.out.println("Error: Unknown operation.");
            }
            lineNum++;
        }

        System.out.println("\nInfinite loop in Boot Code.\nAccumulator: " + accumulator + " Line: " + lineNum);
    }

    private boolean checkForInfinite(int lineNum) {
        return repeat[lineNum];
    }
}
