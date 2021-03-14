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
//         Part 2: Now find the earliest timestamp such that the first bus ID departs at that time and each subsequent listed bus
//                 ID departs at that subsequent minute. (The first line in your input is no longer relevant.)
//                 For example, suppose you have the same list of bus IDs as above:
//                     7,13,x,x,59,x,31,19
//                     An x in the schedule means there are no constraints on what bus IDs must depart at that time.
//
//                     This means you are looking for the earliest timestamp (called t) such that:
//                          Bus ID 7 departs at timestamp t.
//                          Bus ID 13 departs one minute after timestamp t.
//                          There are no requirements or restrictions on departures at two or three minutes after timestamp t.
//                          Bus ID 59 departs four minutes after timestamp t.
//                          There are no requirements or restrictions on departures at five minutes after timestamp t.
//                          Bus ID 31 departs six minutes after timestamp t.
//                          Bus ID 19 departs seven minutes after timestamp t.
//                     The only bus departures that matter are the listed bus IDs at their specific offsets from t. Those bus IDs can depart at other times, and other bus IDs can depart at those times. For example, in the list above, because bus ID 19 must depart seven minutes after the timestamp at which bus ID 7 departs, bus ID 7 will always also be departing with bus ID 19 at seven minutes after timestamp t.
//                     In this example, the earliest timestamp at which this occurs is 1068781:
//Incorrect answers: 197550780366240 - to low
//Correct answer: 538703333547789

import java.util.ArrayList;
import java.util.Arrays;
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
        System.out.println("Wait time is " + waitTime + " mins.");
        System.out.println("So, answer is: " + waitTime * bus);

        System.out.println("\n======\nPart 2\n======");

        long time = Long.parseLong(newInput[0]);
        long increment = Long.parseLong(newInput[0]);
        long index = 1;

        for (int i = 1; i < newInput.length; i++) {
            if (newInput[i].equals("x")) {
                index++;
            } else {
                while ((time + index) % Long.parseLong(newInput[i]) != 0) {
                    time += increment;
                }
                increment *= Long.parseLong(newInput[i]);
                index++;
                //System.out.println(time + "===============================");
            }
        }
        System.out.println("The earliest timestamp, matching the condition is: " + time);
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
