package besttod.com;

//Day 14 - Part 1: The bitmask is always given as a string of 36 bits, written with the most significant bit (representing 2^35)
//                 on the left and the least significant bit (2^0, that is, the 1s bit) on the right. The current bitmask is applied
//                 to values immediately before they are written to memory: a 0 or 1 overwrites the corresponding bit in the value,
//                 while an X leaves the bit in the value unchanged. For example, consider the following program:
//                       mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
//                       mem[8] = 11
//                       mem[7] = 101
//                       mem[8] = 0
//                 This program starts by specifying a bitmask (mask = ....). The mask it specifies will overwrite two bits in every
//                 written value: the 2s bit is overwritten with 0, and the 64s bit is overwritten with 1.
//                 The program then attempts to write the value 11 to memory address 8. By expanding everything out to individual bits,
//                 the mask is applied as follows:
//                     value:  000000000000000000000000000000001011  (decimal 11)
//                     mask:   XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
//                     result: 000000000000000000000000000001001001  (decimal 73)
//                 So, because of the mask, the value 73 is written to memory address 8 instead. Then, the program tries to write 101
//                 to address 7:
//                     value:  000000000000000000000000000001100101  (decimal 101)
//                     mask:   XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
//                     result: 000000000000000000000000000001100101  (decimal 101)
//                 This time, the mask has no effect, as the bits it overwrote were already the values the mask tried to set. Finally,
//                 the program tries to write 0 to address 8:
//                     value:  000000000000000000000000000000000000  (decimal 0)
//                     mask:   XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
//                     result: 000000000000000000000000000001000000  (decimal 64)
//                 64 is written to address 8 instead, overwriting the value that was there previously.
//
//                 What is the sum of all values left in memory after the initialization program completes?
//Correct answer: 13496669152158
//         Part 2:
//Correct answer:

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day14_input.txt";

    public Day14() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        Map<Integer, Long> memory = new HashMap<>();
        String mask = "";

        for (String s : input) {
            if (s.startsWith("mask")) {
                mask = s.substring(7);
                System.out.println("mask = " + mask);
            } else {
                int memoryIndex = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
                memory.put(memoryIndex, addMask(mask, binaryValue(getMemoryValue(s))));
                System.out.println(memory);
            }
        }

        System.out.println("The sum of all values in memory: " + memory.values().stream().mapToLong(l -> l).sum());

    }

    private long addMask(String mask, String value) {
        char[] newValue = value.toCharArray();
        for (int i = 0; i < value.length(); i++) {
            if (mask.charAt(i) != 'X') {
                newValue[i] = mask.charAt(i);
            }
        }
        return Long.parseLong(new String(newValue), 2);
    }

    private String binaryValue(int value) {
        String binary = Integer.toBinaryString(value);
        binary = String.format("%36s", binary).replaceAll(" ", "0");
        System.out.println(value + " = " + binary);
        return binary;
    }

    private int getMemoryValue(String s) {
        String[] split = s.split(" ");
        return Integer.parseInt(split[split.length - 1]);
    }

    private Map<Integer, Integer> getMask(String s) {
        Map<Integer, Integer> maskIndex = new HashMap<>();
        String fullMask = s.substring(7);
        for (int i = 0; i < fullMask.length(); i++) {
            if (fullMask.charAt(i) != 'X') {
                maskIndex.put(i, Integer.parseInt(String.valueOf(fullMask.charAt(i))));
            }
        }
        System.out.println(maskIndex);
        return maskIndex;
    }
}
