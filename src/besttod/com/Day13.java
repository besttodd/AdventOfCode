package besttod.com;

//Day 13 - Part 1: Bus schedules are defined based on a timestamp that measures the number of minutes since some fixed reference
//                 point in the past. At timestamp 0, every bus simultaneously departed from the sea port. After that, each bus
//                 travels to the airport, then various other locations, and finally returns to the sea port to repeat its journey
//                 forever. The time this loop takes a particular bus is also its ID number: the bus with ID 5 departs from the
//                 sea port at timestamps 0, 5, 10, 15, and so on. The bus with ID 11 departs at 0, 11, 22, 33, and so on.
//                 If you are there when the bus departs, you can ride that bus to the airport!
//
//                 Your notes (puzzle input) consist of two lines.
//                     The first line is your estimate of the earliest timestamp you could depart on a bus.
//                     The second line lists the bus IDs that are in service according to the shuttle company;
//                     entries that show x must be out of service, so you decide to ignore them.
//                 To save time once you arrive, your goal is to figure out the earliest bus you can take to the airport. (There will be exactly one such bus.)
//Correct answer: 5257
//
//         Part 2:

import java.util.ArrayList;
import java.util.List;

public class Day13 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day13_input.txt";
    int bus = 0;

    public Day13() {
        List<String> input = new ReadFile().readFile(FILEPATH);
        String[] newInput = input.get(1).split(",");
        List<Integer> busIDs = new ArrayList<>();
        for (String s : newInput) {
            if (!s.equals("x")) {
                busIDs.add(Integer.parseInt(s));
            }
        }
        System.out.println(busIDs);

        int waitTime = findNextBus(busIDs, Integer.parseInt(input.get(0)));
        System.out.println("Wait time is: " + waitTime + "mins.");
        System.out.println("So, answer is: " + waitTime * bus);

        System.out.println("Part 2=================");
    }

    private int findNextBus(List<Integer> busIDs, int timestamp) {
        int lowest = 9999999;
        for (int id : busIDs) {
            int nextBus = Math.round(timestamp / (float) id) * id;
            if (nextBus < lowest && nextBus > timestamp) {
                lowest = nextBus;
                bus = id;
            }
        }
        return lowest - timestamp;
    }
}
