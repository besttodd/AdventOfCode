package besttod.com;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Day 1: Part 1 - Find the TWO entries that sum to ANSWER and then multiply those two numbers together.
//Correct answer: 41979
//       Part 2 - Find the THREE entries that sum to ANSWER and get the multiplication of these entries?
//Correct answer: 193416912


public class Day1 {
    private final static int ANSWER = 2020;
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day1_input.txt";

    private static List<Integer> input = new ArrayList<>();
    private static int entryA;
    private static int entryB;
    private static int entryC;

    public Day1() {
        readFile();

        if (find2Entries(input)) {
            System.out.println(entryA + " + " + entryB + " = " + ANSWER);
            System.out.println("So:\n" + entryA + " * " + entryB + " = " + entryA * entryB + "\n");
        }
        if (find3Entries(input)) {
            System.out.println(entryA + " + " + entryB + " + " + entryC + " = " + ANSWER);
            System.out.println("So:\n" + entryA + " * " + entryB + " * " + entryC + " = " + entryA * entryB * entryC);
        } else {
            System.out.println("No entries found that match the criteria.");
        }
    }

    private void readFile() {
        File inputFile = new File(FILEPATH);

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                input.add(Integer.parseInt(nextLine));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
    }


    private boolean find2Entries(List<Integer> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 1; j < input.size(); j++) {
                if (input.get(i) + input.get(j) == ANSWER) {
                    entryA = input.get(i);
                    entryB = input.get(j);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean find3Entries(List<Integer> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 1; j < input.size(); j++) {
                for (int k = 2; k < input.size(); k++) {
                    if (input.get(i) + input.get(j) + input.get(k) == ANSWER) {
                        entryA = input.get(i);
                        entryB = input.get(j);
                        entryC = input.get(k);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
