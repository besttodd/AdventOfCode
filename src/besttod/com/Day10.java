package besttod.com;

//Day 10 - Part 1: A bunch of adapters are rated for a specific output joltage (your puzzle input). Any given adapter can take an input
//                 1, 2, or 3 jolts lower than its rating and still produce its rated output joltage. In addition, your device has a
//                 built-in joltage adapter rated for 3 jolts higher than the highest-rated adapter in the bunch.
//                 (If your adapter list were 3, 9, and 6, your device's built-in adapter would be rated for 12 jolts.)
//                 The charging outlet you're using has an effective joltage rating of 0. If you use every adapter in your bag at once,
//                 what is the distribution of joltage differences between the charging outlet, the adapters, and your device?
//
//                 Find a chain that uses all of your adapters to connect the charging outlet to your device's built-in adapter and
//                 count the joltage differences between the charging outlet, the adapters, and your device.
//                 What is the number of 1-jolt differences multiplied by the number of 3-jolt differences?
//Correct answer: 2312.

//         Part 2: What is the total number of distinct ways you can arrange the adapters to connect the charging outlet to your device?
//Correct answer:

import java.util.ArrayList;
import java.util.List;

public class Day10 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day10_input.txt";

    public Day10() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        List<Integer> intInput = new ArrayList<>();
        for (String s : input) {
            intInput.add(Integer.parseInt(s));
        }
        intInput.add(findHighest(intInput) + 3);
        System.out.println("Built-In: " + intInput.get(intInput.size() - 1));

        List<Integer> order = new ArrayList<>();
        int joltage = 0;
        int next = 1;
        int diffOf1 = 0;
        int diffOf2 = 0;
        int diffOf3 = 0;

        while (joltage < findHighest(intInput)) {
            if (intInput.contains(next)) {
                joltage = intInput.get(intInput.indexOf(next));
                order.add(joltage);
                diffOf1++;
                next++;
            } else if (intInput.contains(next + 1)) {
                joltage = intInput.get(intInput.indexOf(next + 1));
                order.add(joltage);
                diffOf2++;
                next += 2;
            } else if (intInput.contains(next + 2)) {
                joltage = intInput.get(intInput.indexOf(next + 2));
                order.add(joltage);
                diffOf3++;
                next += 3;
            }
        }

        System.out.println(diffOf1 + " differences of 1 jolt & " + diffOf3 + " differenecs of 3 jolts.");
        System.out.println("Answer: " + diffOf1 * diffOf3);
    }

    private int findHighest(List<Integer> list) {
        int highest = 0;
        for (Integer integer : list) {
            if (integer > highest) {
                highest = integer;
            }
        }
        return highest;
    }
}
