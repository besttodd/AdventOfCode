package besttod.com;

//Day 9: Part 1 - Starting with a preamble of 25 numbers. Each next number should be the sum of any two of the 25 immediately previous numbers.
//                The two numbers will have different values, and there might be more than one such pair. For example, suppose your preamble
//                consists of the numbers 1 through 25 in a random order. To be valid, the next number must be the sum of any two of those numbers.
//                Suppose the 26th number is 45, and the first number (no longer an option, as it is more than 25 numbers ago) was 20.
//                Now, for the next number to be valid, there needs to be some pair of numbers among 1-19, 21-25, or 45 that add up to it.
//                Find the first number in the list (after the preamble) which is not the sum of two of the 25 numbers before it.
//Incorrect answers: 35
//Correct answer: 27911108
//       Part 2 - Now find a contiguous set of at least two numbers in your list which sum to the invalid number from step 1.
//                Find the encryption weakness by adding together the smallest and largest number in this contiguous range;
//Incorrect answers: 2945691
//Correct answer: 4023754


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day9 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day9_input.txt";
    private final static int PREAMBLE = 25;

    public Day9() {
        List<String> input = new ReadFile().readFile(FILEPATH);

        List<Integer> newInput = new ArrayList<>();
        int start = 0;
        int next = PREAMBLE;
        int current = Integer.parseInt(input.get(next));

        while (next < input.size()) {
            for (int i = start; i < next; i++) {
                newInput.add(Integer.parseInt(input.get(i)));
            }
            //System.out.println(newInput);
            if (!increment(newInput, current)) {
                System.out.println("\nInvalid number: " + input.get(next) + "\n");
                //DO PART 2
                newInput.clear();
                for (int i = 0; i < next; i++) {
                    newInput.add(Integer.parseInt(input.get(i)));
                }
                findEncryptionWeakness(newInput, current);
                break;
            }
            newInput.clear();
            start++;
            next++;
            current = Integer.parseInt(input.get(next));
        }
    }

    public boolean increment(List<Integer> list, int current) {
        for (int i = list.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (list.get(i) + list.get(j) == current) {
                    System.out.println(list.get(i) + "+" + list.get(j) + ">>>>>>>>>>>>>>>>>>>>>TRUE");
                    return true;
                }
                //System.out.println(list.get(i) + "+" + list.get(j) + ">>>>>>>>>>>>>>>>>>>>>FALSE");
            }
        }
        return false;
    }

    private void findEncryptionWeakness(List<Integer> list, int current) {
        double total;
        List<Integer> sum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            sum.add(list.get(i));
            for (int j = i + 1; j < list.size(); j++) {
                sum.add(list.get(j));
                total = sum.stream().reduce(0, Integer::sum);
                while (total > current) {
                    sum.remove(0);
                    total = sum.stream().reduce(0, Integer::sum);
                }
                if (total == current) {
                    System.out.println(sum + ">>>>>>>>>>>>>>>>>>>>>TRUE");
                    int encryptWeakness = IntStream.of(lowestHighest(sum)).sum();
                    System.out.println("\nEncryption Weakness: " + encryptWeakness + ".");
                    return;
                }
                //System.out.println(sum + ">>>>>>>>>>>>>>>>>>>>>FALSE"+ total);
            }
        }
    }

    private int[] lowestHighest(List<Integer> list) {
        int[] lowHigh = new int[]{9999999, 0};
        for (Integer integer : list) {
            if (integer < lowHigh[0]) {
                lowHigh[0] = integer;
            }
            if (integer > lowHigh[1]) {
                lowHigh[1] = integer;
            }
        }
        return lowHigh;
    }
}
