package besttod.com;

//Day 11 - Part 1: An airport seat layout fits neatly on a grid. Each position is either floor (.), an empty seat (L), or an
//                 occupied seat (#). Now, you just need to model the people who will be arriving shortly. Fortunately, people are
//                 entirely predictable and always follow a simple set of rules. All decisions are based on the number of occupied
//                 seats adjacent to a given seat (one of the eight positions immediately up, down, left, right, or diagonal from the seat).
//
//                 The following rules are applied to every seat simultaneously:
//                    - If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
//                    - If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
//                    - Otherwise, the seat's state does not change.
//                    Floor (.) never changes; seats don't move, and nobody sits on the floor.
//
//                 Eventually, something interesting happens: the chaos stabilizes and further applications of these rules cause
//                 no seats to change state! Once people stop moving around, count the occupied seats.
//Correct answer: 2316
//
//         Part 2: Now, instead of considering just the eight immediately adjacent seats, consider the first seat in each of those
//                 eight directions. It also now takes five or more visible occupied seats for an occupied seat to become empty
//                 (rather than four or more from the previous rules). The other rules still apply: empty seats that see no occupied
//                 seats become occupied, seats matching no rule don't change, and floor never changes. Given the new visibility
//                 method and the rule change for occupied seats becoming empty, once equilibrium is reached, how many seats end
//                 up occupied?
//Correct answer: 2128

import java.util.Arrays;
import java.util.List;

public class Day11 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day11_input.txt";
    private final static String PART1 = "adjacent";
    private final static String PART2 = "visible";

    public Day11() {
        List<String> input = new ReadFile().readFile(FILEPATH);
        String[] stringInput = new String[input.size()];
        input.toArray(input.toArray(stringInput));

        SeatLayout layout = new SeatLayout(stringInput);
        SeatLayout newLayout;
        SeatLayout previousLayout = layout;
        boolean stable = false;
        int count = 0;

        layout.showLayout();
        while (!stable) {
            newLayout = newRound(previousLayout, PART2);
            if (Arrays.deepToString(newLayout.getLayout()).equals(Arrays.deepToString(previousLayout.getLayout()))) {
                stable = true;
            } else {
                previousLayout = newLayout;
                count++;
            }
        }
        System.out.println(count + " rounds");
        System.out.println(previousLayout.getOccupiedSeats() + " occupied seats");
    }

    private SeatLayout newRound(SeatLayout layout, String pref) {
        char[][] grid = layout.getLayout();
        char[][] newGrid = new char[grid.length][grid[0].length];
        String[] temp = new String[grid.length];
        StringBuilder temp2 = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case 'L':
                        if (layout.countOccupied(i, j, pref) == 0) {
                            newGrid[i][j] = '#';
                            layout.seatOccupied();
                        } else {
                            newGrid[i][j] = 'L';
                        }
                        break;
                    case '#':
                        if (layout.countOccupied(i, j, pref) >= 5) {
                            newGrid[i][j] = 'L';
                        } else {
                            newGrid[i][j] = '#';
                            layout.seatOccupied();
                        }
                        break;
                    default:
                        newGrid[i][j] = '.';
                        break;
                }
            }
            for (int k = 0; k < newGrid[i].length; k++) {
                temp2.append(newGrid[i][k]);
            }
            temp[i] = temp2.toString();
            temp2.setLength(0);
            System.out.println(temp[i] + "  ==");
        }
        System.out.println("?????????????????????????????????????????????????????????????????????????????????????????");
        return new SeatLayout(temp);
    }
}
