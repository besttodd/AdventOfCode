package besttod.com;

//Day 7: Part 1 - Many rules are being enforced about bags and their contents; bags must be color-coded and
//                must contain specific quantities of other color-coded bags.
//                You have a shiny gold bag. If you wanted to carry it in at least one other bag,
//                how many different bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually,
//                contain at least one shiny gold bag?)
//Incorrect answers: 44, 89
//Correct answer: 161
//       Part 2 - Now, How many individual bags are required inside your single shiny gold bag?
//Correct answer:

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day7_input.txt";
    private static final Pattern VALID_PATTERN = Pattern.compile("[a-z]+|[0-9]+");

    Set<String> containShinyGold = new HashSet<>();

    public Day7() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        Queue<String> queue1 = new LinkedList<>(checkRules(input, "shiny gold", false));
        System.out.println("=========================================================================================");

        while (!queue1.isEmpty()) {
            String next = queue1.poll();
            for (String s : input) {
                if (s.contains(next)) {
                    List<String> bagInfo = extractBags(s, next, false);
                    String bag = bagInfo.get(0) + " " + bagInfo.get(1);
                    if (!bag.equals(next)) {
                        queue1.add(bag);
                        containShinyGold.add(bag);
                        System.out.println("Queue: " + queue1.size() + "::" + queue1);
                    }
                }
            }
        }
        System.out.println("\nNumber of colors to hold a 'Shiny Gold Bag': " + containShinyGold.size() + "========================================");
        containShinyGold.clear();

        Queue<String> queue2 = new LinkedList<>(checkRules(input, "shiny gold", true));

        while (!queue2.isEmpty()) {
            String next = queue2.poll();
            for (String s : input) {
                if (s.contains(next)) {
                    List<String> bagInfo = extractBags(s, next, false);
                    String bag = bagInfo.get(0) + " " + bagInfo.get(1);
                    if (!bag.equals(next)) {
                        queue2.add(bag);
                        containShinyGold.add(bag);
                    }
                }
            }
        }

        System.out.println("\nNumber of bags inside a 'Shiny Gold Bag': " + containShinyGold.size());
    }

    private Queue<String> checkRules(List<String> input, String search, boolean inside) {
        Queue<String> queue1 = new LinkedList<>();
        for (String s : input) {
            if (s.contains(search)) {
                List<String> bagInfo = extractBags(s, search, inside);
                String bag = bagInfo.get(0) + " " + bagInfo.get(1);
                if (!bag.equals(search)) {
                    containShinyGold.add(bag);
                    queue1.add(bag);
                }
            }
        }
        return queue1;
    }

    private List<String> extractBags(String s, String search, boolean inside) {
        List<String> bagNContents = parse(s);
        List<String> contents = new ArrayList<>();
        int index = bagNContents.indexOf("contain") + 2; //start of outer bags contents, ignoring the number
        if (!(bagNContents.get(0) + bagNContents.get(1)).equals(search)) {
            for (int i = index; i < bagNContents.size(); i = i + 4) {
                String singleBag = bagNContents.get(i) + " " + bagNContents.get(i + 1);
                contents.add(singleBag);
            }
        }
        System.out.println(bagNContents);
        System.out.println(contents + "===================================");
        if (inside) {
            return contents;
        } else {
            return bagNContents;
        }
    }

    private List<String> parse(String toParse) {
        List<String> chunks = new LinkedList<>();
        Matcher matcher = VALID_PATTERN.matcher(toParse);
        while (matcher.find()) {
            chunks.add(matcher.group());
        }
        return chunks;
    }
}
