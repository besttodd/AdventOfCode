package besttod.com;

//Day 7: Part 1 - Many rules are being enforced about bags and their contents; bags must be color-coded and
//                must contain specific quantities of other color-coded bags.
//                You have a shiny gold bag. If you wanted to carry it in at least one other bag,
//                how many different bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually,
//                contain at least one shiny gold bag?)
//Incorrect answers: 44, 89
//Correct answer: 161
//       Part 2 - Now, How many individual bags are required inside your single shiny gold bag?
//Incorrect answers: 203, 1052, 7482 to low 8303, 30900 to high
//Correct answer: 30899

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day7_input.txt";
    private static final Pattern VALID_PATTERN = Pattern.compile("[a-z]+|[0-9]+");

    Set<String> containShinyGold = new HashSet<>();

    public Day7() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        Queue<String> queue1 = new LinkedList<>(checkRules(input, "shiny gold"));
        containShinyGold.addAll(queue1);
        System.out.println(queue1);

        while (!queue1.isEmpty()) {
            String next = queue1.poll();
            queue1.addAll(checkRules(input, next));
            containShinyGold.addAll(queue1);
        }
        System.out.println("\nNumber of colors to hold a 'Shiny Gold Bag': " + containShinyGold.size());
        containShinyGold.clear();
        System.out.println("=========================================================================================");

        List<Bag_Day7> allBags = new ArrayList<>();
        for (String s : input) {
            allBags.add(new Bag_Day7(s));
        }

        for (Bag_Day7 bag : allBags) {
            if (bag.getColor().equals("shiny gold")) {
                System.out.println("\nNumber of bags inside a 'Shiny Gold Bag': " + (bag.containsNumOfBags(allBags) - 1));
                break;
            }
        }
    }

    private Queue<String> checkRules(List<String> input, String search) {
        Queue<String> queue1 = new LinkedList<>();
        for (String s : input) {
            if (s.contains(search)) {
                List<String> bagInfo = parse(s);
                String bag = bagInfo.get(0) + " " + bagInfo.get(1);
                if (!bag.equals(search)) {
                    queue1.add(bag);
                }
            }
        }
        return queue1;
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
