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
//
//         Part 2: A version 2 decoder chip doesn't modify the values being written at all. Instead, it acts as a memory address
//                 decoder. Immediately before a value is written to memory, each bit in the bitmask modifies the corresponding bit of
//                 the destination memory address in the following way:
//                     If the bitmask bit is 0, the corresponding memory address bit is unchanged.
//                     If the bitmask bit is 1, the corresponding memory address bit is overwritten with 1.
//                     If the bitmask bit is X, the corresponding memory address bit is floating.
//                 A floating bit is not connected to anything and instead fluctuates unpredictably. In practice, this means the floating
//                 bits will take on all possible values, potentially causing many memory addresses to be written all at once!
//
//                 What is the sum of all values left in memory after it completes?
//Correct answer: 3278997609887

import java.util.*;

public class Day14 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day14_input.txt";

    public Day14() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        Map<Long, Long> memory = new HashMap<>();
        String mask = "";

        for (String s : input) {
            if (s.startsWith("mask")) {
                mask = s.substring(7);
                System.out.println("mask = " + mask);
            } else {
                long memoryIndex = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
                memory.put(memoryIndex, addMask(mask, binaryValue(getMemoryValue(s))));
                System.out.println(memory);
            }
        }

        System.out.println("\nThe sum of all values in memory: " + memory.values().stream().mapToLong(l -> l).sum());

        System.out.println("\n========\nPart 2\n========");

        memory.clear();

        for (String s : input) {
            if (s.startsWith("mask")) {
                mask = s.substring(7);
                System.out.println("mk = " + mask);
            } else {
                int memoryIndex = Integer.parseInt(s.substring(s.indexOf("[") + 1, s.indexOf("]")));
                long[] memAddresses = getDecodedAddresses(memoryIndex, mask);
                for (long memAddress : memAddresses) {
                    memory.put(memAddress, (long) getMemoryValue(s));
                }
            }
        }
        System.out.println("\nThe sum of all values in memory: " + memory.values().stream().mapToLong(l -> l).sum());
    }

    private long[] getDecodedAddresses(int index, String mask) {
        String binaryIndex = binaryValue(index);
        StringBuilder tempResult = new StringBuilder();
        List<Integer> xIndexes = new ArrayList<>();
        //Add the mask to the address
        for (int i = 0; i < mask.length(); i++) {
            switch (mask.charAt(i)) {
                case 'X':
                    tempResult.append('X');
                    xIndexes.add(i);
                    break;
                case '1':
                    tempResult.append('1');
                    break;
                case '0':
                    tempResult.append(binaryIndex.charAt(i));
                    break;
            }
        }
        String result = tempResult.toString();

        //Get the variations for X's
        long[] indexes = new long[(int) Math.pow(2, numXs(result))];
        int count = 0;
        System.out.println(Math.pow(2, numXs(result)));
        while (count < Math.pow(2, numXs(result))) {
            indexes[count] = nextVariation(result.toCharArray(), count, xIndexes);
            count++;
        }
        return indexes;
    }

    private long nextVariation(char[] binaryIndex, int iteration, List<Integer> xIndexes) {
        String variableBits = binaryValue(iteration);
        variableBits = variableBits.substring(variableBits.length() - xIndexes.size());
        char[] tempBits = variableBits.toCharArray();
        Iterator<Integer> indexIt = xIndexes.iterator();
        for (char tempBit : tempBits) {
            binaryIndex[indexIt.next()] = tempBit;
        }
        System.out.println(binaryIndex);
        return Long.parseLong(new String(binaryIndex), 2);
    }

    private int numXs(String mask) {
        int count = 0;
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == 'X') {
                count++;
            }
        }
        System.out.println("Num of X's: " + count);
        return count;
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
}
