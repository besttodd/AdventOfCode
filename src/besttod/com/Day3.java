package besttod.com;

//Day 3: Part 1 - Starting at the top-left corner you need to count the number of trees (#) in the way on the slope to below the last line.
//                The toboggan can only follow a specific slope: RIGHT 3, DOWN 1.
//                From the starting position, check the position that is right 3 and down 1.
//                Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.
//Incorrect answers: 81, 64, 247, 63
//       Part 2 - Now, from the same staring position, determine the number of trees you would encounter taking the following slopes:
//                Right 1, down 1.
//                Right 3, down 1. (Part 1)
//                Right 5, down 1.
//                Right 7, down 1.
//                Right 1, down 2.
//
//                Multiply the total number of trees to get a final answer.
//Incorrect answers: 1205199104, 5500166400

import java.util.List;

public class Day3 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day3_input.txt";

    private List<String> input;

    public Day3() {
        input = new ReadFile().readFile(FILEPATH);

        input.remove(0); //ignore the starting line

        int slope1 = checkPath(1, 1);
        int slope2 = checkPath(3, 1);
        int slope3 = checkPath(5, 1);
        int slope4 = checkPath(7, 1);
        int slope5 = checkPath(1, 2);

        long total = (long) slope1 * slope2 * slope3 * slope4 * slope5;

        System.out.println("Slope 1: " + slope1 + " Slope 2: " + slope2 + " Slope 3: " + slope3 + " Slope 4: " + slope4 + " Slope 5: " + slope5);
        System.out.println("Total Trees: " + total);
    }

    private int checkPath(int right, int down) {
        int trees = 0;
        int progress = right;

        for (int i = down - 1; i < input.size(); i = i + down) {
            if (progress > input.get(i).length() - 1) {
                progress = progress - input.get(i).length();
            }
            if (input.get(i).charAt(progress) == '#') {
                trees++;
            }
            System.out.println("Line: " + i + "  Trees: " + trees + "  Progress: " + progress);
            progress = progress + right;
        }
        return trees;
    }
}
