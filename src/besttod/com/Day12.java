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
//
//         Part 2: Now the actions indicate how to move a waypoint which is relative to the ship's position:
//                         Action N means to move the waypoint north by the given value.
//                         Action S means to move the waypoint south by the given value.
//                         Action E means to move the waypoint east by the given value.
//                         Action W means to move the waypoint west by the given value.
//                         Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
//                         Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
//                         Action F means to move forward to the waypoint a number of times equal to the given value.
//
//                 The waypoint starts 10 units east and 1 unit north relative to the ship. The waypoint is relative to the ship;
//                 that is, if the ship moves, the waypoint moves with it. For example, using the same instructions as above:
//                         F10 moves the ship to the waypoint 10 times (a total of 100 units east and 10 units north), leaving the ship at east 100, north 10. The waypoint stays 10 units east and 1 unit north of the ship.
//                         N3 moves the waypoint 3 units north to 10 units east and 4 units north of the ship. The ship remains at east 100, north 10.
//                         F7 moves the ship to the waypoint 7 times (a total of 70 units east and 28 units north), leaving the ship at east 170, north 38. The waypoint stays 10 units east and 4 units north of the ship.
//                         R90 rotates the waypoint around the ship clockwise 90 degrees, moving it to 4 units east and 10 units south of the ship. The ship remains at east 170, north 38.
//                         F11 moves the ship to the waypoint 11 times (a total of 44 units east and 110 units south), leaving the ship at east 214, south 72. The waypoint stays 4 units east and 10 units south of the ship.
//                 After these operations, the ship's Manhattan distance from its starting position is 214 + 72 = 286.
//                 Figure out where the navigation instructions actually lead. What is the Manhattan distance between that
//                 location and the ship's starting position?
//Incorrect answers: 38611, 29851 too high
//Correct answer: 29839

import java.util.Arrays;
import java.util.List;

public class Day12 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day12_input.txt";

    int east;
    int north;
    double wpEast = 10;
    double wpNorth = 1;

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
                    moveBoat(Integer.parseInt(s.substring(1)), direction);
                    break;
                default:
                    moveBoat(Integer.parseInt(s.substring(1)), s.charAt(0));
            }
        }

        showPosition();


        System.out.println("\nPart 2:");
        east = 0;
        north = 0;

        for (String s : input) {
            double temp = wpEast;
            double angle = Math.toRadians(Integer.parseInt(s.substring(1)));
            switch (s.charAt(0)) {
                case 'L':
                    wpEast = Math.round(temp * Math.cos(angle) - wpNorth * Math.sin(angle));
                    wpNorth = Math.round(temp * Math.sin(angle) + wpNorth * Math.cos(angle));
                    break;
                case 'R':
                    wpEast = Math.round(temp * Math.cos(angle) - (wpNorth * -1.0) * Math.sin(angle));
                    wpNorth = Math.round((temp * -1.0) * Math.sin(angle) + wpNorth * Math.cos(angle));
                    break;
                case 'F':
                    moveBoat2(Integer.parseInt(s.substring(1)));
                    break;
                default:
                    moveWP(Integer.parseInt(s.substring(1)), s.charAt(0));
            }
        }
        showPosition();
    }

    private void showPosition() {
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

    private void moveBoat(int spaces, char direction) {
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

    private void moveWP(int spaces, char direction) {
        switch (direction) {
            case 'N':
                wpNorth += spaces;
                break;
            case 'S':
                wpNorth -= spaces;
                break;
            case 'E':
                wpEast += spaces;
                break;
            case 'W':
                wpEast -= spaces;
                break;
        }
    }

    private void moveBoat2(int spaces) {
        east += spaces * wpEast;
        north += spaces * wpNorth;
    }

}
