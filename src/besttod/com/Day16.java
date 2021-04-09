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
Correct answer: 23954

Part B:
Using the valid ranges for each field, determine what order the fields appear on the tickets. The order is consistent between
all tickets: if seat is the third field, it is the third field on every ticket, including your ticket.

Once you work out which field is which, look for the six fields on your ticket that start with the word departure.
What do you get if you multiply those six values together?

Incorrect answers: 560
Correct answer: 453459307723
*/

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day16_input.txt";

    public Day16() {
        File inputFile = new File(FILEPATH);
        Map<String, Field> ranges = new HashMap<>();
        List<String> rangeLabels = new ArrayList<>();
        int[] myTicket = new int[0];
        List<int[]> nearbyTickets = new ArrayList<>();
        List<Integer> validValue = new ArrayList<>();
        List<Integer> invalidValues = new ArrayList<>();
        boolean nearby = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                if (nextLine.contains("-")) {
                    String temp = nextLine.substring(nextLine.indexOf(":") + 2);
                    String label = nextLine.substring(0, nextLine.indexOf(":"));
                    String[] lowHigh = temp.split("or");
                    String[] minMax = lowHigh[0].split("-");
                    int min = Integer.parseInt(minMax[0].trim());
                    int max = Integer.parseInt(minMax[1].trim());
                    minMax = lowHigh[1].split("-");
                    int min2 = Integer.parseInt(minMax[0].trim());
                    int max2 = Integer.parseInt(minMax[1].trim());
                    rangeLabels.add(label);
                    ranges.put(label, new Field(min, max, min2, max2));
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
            String[] rangeKey = new String[myTicket.length];
            List<int[]> validTickets = new ArrayList<>();
            for (int[] ticket : nearbyTickets) {
                for (int num : ticket) {
                    for (Map.Entry<String, Field> entry : ranges.entrySet()) {
                        if (entry.getValue().isInRange(num)) {
                            validValue.add(num);
                        }
                    }
                }
                List<Integer> temp = Arrays.stream(ticket).boxed().collect(Collectors.toList());
                temp.removeAll(validValue);
                if (temp.size() == 0) {
                    validTickets.add(ticket);
                }
                invalidValues.addAll(temp);
            }
            //System.out.println("Invalid Values: " + invalidValues);
            System.out.println("\nPart A\nError Rate: " + invalidValues.stream().mapToInt(i -> i).sum());


            List<String> invalidRanges = new ArrayList<>();
            List<String> temp = new ArrayList<>(rangeLabels);
            while (Arrays.asList(rangeKey).contains(null)) {
                for (int position = 0; position < myTicket.length; position++) {
                    for (Map.Entry<String, Field> entry : ranges.entrySet()) {
                        for (int[] validTicket : validTickets) {
                            if (!entry.getValue().isInRange(validTicket[position])) {
                                invalidRanges.add(entry.getKey());
                                //System.out.println(ticket + ":" + position + ":" + invalidRanges);
                                break;
                            }

                        }
                    }
                    temp.removeAll(invalidRanges);
                    if (temp.size() == 1) {
                        rangeKey[position] = temp.get(0);
                    }
                    temp.clear();
                    temp.addAll(rangeLabels);
                    for (String s : rangeKey) {
                        temp.remove(s);
                    }
                    invalidRanges.clear();
                }
            }
            System.out.println("\nPart B:");
            System.out.println(Arrays.toString(rangeKey));
            System.out.println(calculatePartB(rangeKey, myTicket));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
    }

    private long calculatePartB(String[] rangeKey, int[] myTicket) {
        long answer = 1;
        for (int z = 0; z < rangeKey.length; z++) {
            if (rangeKey[z].contains("departure")) {
                answer *= myTicket[z];
            }
        }
        return answer;
    }


    private static class Field {
        private final int min1;
        private final int min2;
        private final int max1;
        private final int max2;

        public Field(int min1, int max1, int min2, int max2) {
            this.min1 = min1;
            this.min2 = min2;
            this.max1 = max1;
            this.max2 = max2;
        }

        public boolean isInRange(int num) {
            if (min1 <= num & num <= max1) {
                return true;
            }
            return min2 <= num & num <= max2;
        }
    }
}
