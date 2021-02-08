package besttod.com;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bag_Day7 {
    private static final Pattern VALID_PATTERN = Pattern.compile("[a-z]+|[0-9]+");

    private String color;
    private Map<String, Integer> contents = new HashMap<>();

    public Bag_Day7(String rule) {
        List<String> bagNContents = parse(rule);
        color = bagNContents.get(0) + " " + bagNContents.get(1);
        extractContents(rule, color);
    }

    private List<String> parse(String toParse) {
        List<String> chunks = new LinkedList<>();
        Matcher matcher = VALID_PATTERN.matcher(toParse);
        while (matcher.find()) {
            chunks.add(matcher.group());
        }
        return chunks;
    }

    private void extractContents(String s, String search) {
        List<String> bagNContents = parse(s);
        if (color.equals(search)) {
            for (int i = 4; i < bagNContents.size(); i = i + 4) {
                if (!bagNContents.get(i).equals("no")) {
                    String insideBag = bagNContents.get(i + 1) + " " + bagNContents.get(i + 2);
                    int numBags = Integer.parseInt(bagNContents.get(i));
                    contents.put(insideBag, numBags);
                }
            }
        }
    }

    public String getColor() {
        return color;
    }

    private Bag_Day7 findBag(List<Bag_Day7> allBags, String color) {
        for (Bag_Day7 bag : allBags) {
            if (bag.getColor().equals(color)) {
                return bag;
            }
        }
        return null;
    }

    public long containsNumOfBags(List<Bag_Day7> allBags) {
        long numBags = 1;
        for (Map.Entry<String, Integer> bag : contents.entrySet()) {
            Bag_Day7 foundBag = findBag(allBags, bag.getKey());
            assert foundBag != null;
            numBags += bag.getValue() * foundBag.containsNumOfBags(allBags);
        }
        return numBags;
    }
}
