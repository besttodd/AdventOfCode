package besttod.com;

//Day 6: Part 1 - The customs form asks a series of 26 yes-or-no questions marked a through z.
//                All you need to do is identify the questions for which anyone in your group answers "yes".
//                For each of the people in their group, write down the questions for which they answer "yes", one per line.
//                Each group's answers are separated by a blank line, and within each group, each person's answers are on a single line.
//                For example:
//                             abc                          a
//                                                          a
//                             a                            a
//                             b                            a
//                             c
//                                                          b
//                             ab
//                             ac
//
//                This list represents answers from five groups:
//                     The first group contains one person who answered "yes" to 3 questions: a, b, and c.
//                     The second group contains three people; combined, they answered "yes" to 3 questions: a, b, and c.
//                     The third group contains two people; combined, they answered "yes" to 3 questions: a, b, and c.
//                     The fourth group contains four people; combined, they answered "yes" to only 1 question, a.
//                     The last group contains one person who answered "yes" to only 1 question, b.
//
//                     For each group, count the number of questions to which anyone answered "yes".
//                     What is the sum of those counts?
//Correct answer: 6416
//       Part 2 - Now, you don't need to identify the questions to which anyone answered "yes";
//                you need to identify the questions to which everyone answered "yes".
//                From the list above:
//                     In the first group, everyone (all 1 person) answered "yes" to 3 questions: a, b, and c.
//                     In the second group, there is no question to which everyone answered "yes".
//                     In the third group, everyone answered yes to only 1 question, a. Since some people did not answer "yes" to b or c, they don't count.
//                     In the fourth group, everyone answered yes to only 1 question, a.
//                     In the fifth group, everyone (all 1 person) answered "yes" to 1 question, b.
//
//                     The sum of these counts is 3 + 0 + 1 + 1 + 1 = 6.
//                     For each group, count the number of questions to which everyone answered "yes".
//                     What is the sum of those counts?
//Correct answer: 3050


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Day6 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day6_input.txt";
    private List<String> groups = new ArrayList<>();

    public Day6() {
        readFile();

        int[] countDistinct = new int[groups.size()];
        int[] countDisDup = new int[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            String[] answers = groups.get(i).split(":");

            countDistinct[i] = countDistinct(answers[0]);
            countDisDup[i] = countDistinctDuplicates(answers[0], Integer.parseInt(answers[1]));

            System.out.println(groups.get(i) + "::" + countDistinct[i] + "::" + countDisDup[i]);
        }

        int sum = IntStream.of(countDistinct).sum();
        System.out.println(sum);
        sum = IntStream.of(countDisDup).sum();
        System.out.println(sum);
    }

    private int countDistinctDuplicates(String s, int num) {
        Map<Character, Integer> charFreq = new HashMap<>();
        //count frequency of each character
        for (char c : s.toCharArray()) {
            Integer count = charFreq.get(c);
            int newCount = (count == null ? 1 : count + 1);
            charFreq.put(c, newCount);
        }
        //count number of characters occurring for each line(num)
        for (Map.Entry<Character, Integer> entry : charFreq.entrySet()) {
            Integer count = entry.getValue();
            int groupCount = (count < num ? 0 : 1);
            charFreq.put(entry.getKey(), groupCount);
        }
        return charFreq.values().stream().mapToInt(Integer::intValue).sum();
    }

    private int countDistinct(String s) {
        return (int) s.chars().distinct().count();
    }

    private void readFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
            String nextLine;
            StringBuilder group = new StringBuilder();
            int numLines = 0;
            //while there is no new line read in the lines, combine them to one line and add the number of lines combined at the end
            while ((nextLine = br.readLine()) != null) {
                if (!nextLine.isEmpty()) {
                    group.append(nextLine);
                    numLines++;
                } else {
                    group.append(":").append(numLines);
                    groups.add(group.toString());
                    group = new StringBuilder();
                    numLines = 0;
                }
            }
            //add the last entry
            group.append(":").append(numLines);
            groups.add(group.toString());
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
    }
}
