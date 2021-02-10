package besttod.com;

//Day 12 - Part 1: The navigation instructions (puzzle input) consists of a sequence of single-character actions paired with integer
//                 input values. After staring at them for a few minutes, you work out what they probably mean:
//                       Action N means to move north by the given value.
//                       Action S means to move south by the given value.
//                       Action E means to move east by the given value.
//                       Action W means to move west by the given value.
//                       Action L means to turn left the given number of degrees.
//                       Action R means to turn right the given number of degrees.
//                       Action F means to move forward by the given value in the direction the ship is currently facing.
//                 The ship starts by facing east. Only the L and R actions change the direction the ship is facing.
//                 (That is, if the ship is facing east and the next instruction is N10, the ship would move north 10 units,
//                 but would still move east if the following action were F.)
//                 Figure out where the navigation instructions lead. What is the Manhattan distance (sum of the absolute
//                 values of its east/west position and its north/south position) between that location and the ship's
//                 starting position?
//Correct answer: 2847

import java.util.Arrays;
import java.util.List;

public class Day12 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day12_input.txt";

    int east = 0;
    int north = 0;

    public Day12() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        char direction = 'E';
        List<Character> directions = Arrays.asList('N', 'E', 'S', 'W', 'N', 'E', 'S', 'W');

        for (String s : input) {
            switch (s.charAt(0)) {
                case 'L':
                    direction = directions.get(directions.lastIndexOf(direction) - (Integer.parseInt(s.substring(1)) / 90));
                    break;
                case 'R':
                    direction = directions.get(directions.indexOf(direction) + (Integer.parseInt(s.substring(1)) / 90));
                    break;
                case 'F':
                    move(Integer.parseInt(s.substring(1)), direction);
                    break;
                default:
                    move(Integer.parseInt(s.substring(1)), s.charAt(0));
            }
        }

        String e_w = "east";
        String n_s = "north";
        if (east < 0) {
            e_w = "west";
        }
        if (north < 0) {
            n_s = "south";
        }
        System.out.println("Final position: " + Math.abs(east) + " " + e_w + " " + Math.abs(north) + " " + n_s + ".");
        System.out.println("Manhattan distance: " + (Math.abs(east) + Math.abs(north)) + ".");

    }

    private void move(int spaces, char direction) {
        switch (direction) {
            case 'N':
                north += spaces;
                break;
            case 'S':
                north -= spaces;
                break;
            case 'E':
                east += spaces;
                break;
            case 'W':
                east -= spaces;
                break;
        }
    }

}
