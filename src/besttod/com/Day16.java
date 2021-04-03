package besttod.com;

/*
Day 16 - Part A:
The rules for ticket fields specify a list of fields that exist somewhere on the ticket and the valid ranges of values for each
field. For example, a rule like class: 1-3 or 5-7 means that one of the fields in every ticket is named class and can be any value
in the ranges 1-3 or 5-7 (inclusive, such that 3 and 5 are both valid in this field, but 4 is not).

Each ticket is represented by a single line of comma-separated values. The values are the numbers on the ticket in the order they
appear; every ticket has the same format.

Start by determining which tickets are completely invalid; these are tickets that contain values which aren't valid for any field.
Ignore your ticket for now.

It doesn't matter which position corresponds to which field; you can identify INVAILD nearby tickets by considering only whether
tickets contain values that are not valid for any field. Adding together all of the invalid values produces your ticket scanning
error rate.
Consider the validity of the nearby tickets you scanned. What is your ticket scanning error rate?

Incorrect answers: 14969
Correct answer:
*/

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day16_input.txt";

    public Day16() {
        File inputFile = new File(FILEPATH);
        Map<Integer, Integer> ranges = new HashMap<>();
        int[] myTicket;
        List<int[]> nearbyTickets = new ArrayList<>();
        List<Integer> valid = new ArrayList<>();
        List<Integer> invalidValues = new ArrayList<>();
        boolean nearby = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                if (nextLine.contains("-")) {
                    String temp = nextLine.substring(nextLine.indexOf(":") + 2);
                    String[] lowHigh = temp.split("or");
                    String[] minMax = lowHigh[0].split("-");
                    int min = Integer.parseInt(minMax[0].trim());
                    int max = Integer.parseInt(minMax[1].trim());
                    if (ranges.containsKey(min)) {
                        if (ranges.get(min) < max) {
                            ranges.put(min, max);
                        }
                    } else {
                        ranges.put(min, max);
                    }
                    minMax = lowHigh[1].split("-");
                    min = Integer.parseInt(minMax[0].trim());
                    max = Integer.parseInt(minMax[1].trim());
                    if (ranges.containsKey(min)) {
                        if (ranges.get(min) < max) {
                            ranges.put(min, max);
                        }
                    } else {
                        ranges.put(min, max);
                    }
                } else if (nextLine.contains("ticket")) {
                    if (nextLine.contains("your")) {
                        String[] temp = br.readLine().split(",");
                        myTicket = Arrays.stream(temp).mapToInt(Integer::parseInt).toArray();
                        System.out.println("My ticket: " + Arrays.toString(myTicket));
                    } else {
                        nearby = true;
                    }
                } else if (nearby) {
                    String[] temp = nextLine.split(",");
                    nearbyTickets.add(Arrays.stream(temp).mapToInt(Integer::parseInt).toArray());
                }
            }
            br.close();

            for (int[] ticket : nearbyTickets) {
                for (int num : ticket) {
                    for (Map.Entry<Integer, Integer> entry : ranges.entrySet()) {
                        if (num >= entry.getKey() & num <= entry.getValue()) {
                            valid.add(num);
                            break;
                        }
                    }
                }
                List<Integer> temp = Arrays.stream(ticket).boxed().collect(Collectors.toList());
                temp.removeAll(valid);
                invalidValues.addAll(temp);
            }
            System.out.println(ranges);
            System.out.println("Invalid Values: " + invalidValues);
            System.out.println("Error Rate: " + invalidValues.stream().mapToInt(i -> i).sum());
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
    }
}
