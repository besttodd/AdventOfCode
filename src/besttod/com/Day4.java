package besttod.com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Day 4: Part 1 - The automatic passport scanners are slow because they're having trouble detecting which passports have all required fields.
//                The expected fields are as follows:
//                byr (Birth Year)
//                iyr (Issue Year)
//                eyr (Expiration Year)
//                hgt (Height)
//                hcl (Hair Color)
//                ecl (Eye Color)
//                pid (Passport ID)
//                cid (Country ID)
//                Passport data is validated in batch files. Valid passports must have all 8 fields however cid is the only field that can be excluded.
//                Each passport is represented as a sequence of key:value pairs separated by spaces or newlines. Passports are separated by blank lines.
//Correct answer: 239
//       Part 2 - Now each field has strict rules about what values are valid for automatic validation:
//                byr (Birth Year) - four digits; at least 1920 and at most 2002.
//                iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//                eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//                hgt (Height) - a number followed by either cm or in:
//                    If cm, the number must be at least 150 and at most 193.
//                    If in, the number must be at least 59 and at most 76.
//                hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//                ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//                pid (Passport ID) - a nine-digit number, including leading zeroes.
//                cid (Country ID) - ignored, missing or not.
//                Count the passports where all required fields are both present and valid according to the above rules.
//Correct answer: 188

public class Day4 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day4_input.txt";
    private List<String[]> passports = new ArrayList<>();

    public Day4() {
        readFile();

        int validPassports = 0;
        String[] pairValues;
        HashMap<String, String> keyPairs = new HashMap<>();

        for (String[] passport : passports) { //Each passport
            for (int j = 0; j < passport.length; j++) { //Each line of the passport
                String[] fields = passport[j].split(" ");
                if (!fields[0].matches("\\?|valid|invalid")) {
                    for (int k = 0; k < fields.length; k++) { //Each field on the line
                        pairValues = fields[k].split(":");
                        pairValues[0] = checkField(pairValues[0]);
                        if (checkValue(pairValues).equals("invalid")) {
                            passport[passport.length - 1] = "invalid";
                            k = fields.length;
                            j = passport.length;
                        } else {
                            keyPairs.put(pairValues[0], pairValues[1]);
                            passport[passport.length - 1] = "valid";
                        }
                    }
                }
            }
            if (keyPairs.size() == 8) {
                validPassports++;
            } else if (keyPairs.size() == 7 && !keyPairs.containsKey("cid")) {
                validPassports++;
            } else passport[passport.length - 1] = "invalid";

            System.out.println(keyPairs);
            System.out.println(passport[passport.length - 1]);

            keyPairs.clear();
            System.out.println("===== Next Passport =====");
        }

        System.out.println("\nTotal Passports: " + passports.size() + " Valid Passports:" + validPassports);
    }

    private String checkValue(String[] pair) {
        switch (pair[0]) {
            case "byr":
                if (Integer.parseInt(pair[1]) >= 1920 && Integer.parseInt(pair[1]) <= 2003) {
                    return "valid";
                } else {
                    return "invalid";
                }
            case "iyr":
                if (Integer.parseInt(pair[1]) >= 2010 && Integer.parseInt(pair[1]) <= 2020) {
                    return "valid";
                } else {
                    return "invalid";
                }
            case "eyr":
                if (Integer.parseInt(pair[1]) >= 2020 && Integer.parseInt(pair[1]) <= 2030) {
                    return "valid";
                } else {
                    return "invalid";
                }
            case "hgt":
                String[] height = pair[1].split("cm|in");
                if (pair[1].contains("cm")) {
                    if (Integer.parseInt(height[0]) >= 150 && Integer.parseInt(height[0]) <= 193) {
                        return "valid";
                    } else return "invalid";
                } else if (Integer.parseInt(height[0]) >= 59 && Integer.parseInt(height[0]) <= 76) {
                    return "valid";
                } else return "invalid";
            case "hcl":
                if (pair[1].contains("#") && pair[1].length() == 7) {
                    String numbers = pair[1].substring(1);
                    if (numbers.matches("[a-fA-F0-9]+")) {
                        return "valid";
                    }
                } else return "invalid";
            case "ecl":
                switch (pair[1].toLowerCase()) {
                    case "amb":
                    case "blu":
                    case "brn":
                    case "gry":
                    case "grn":
                    case "hzl":
                    case "oth":
                        return "valid";
                    default:
                        return "invalid";
                }
            case "pid":
                if (pair[1].matches("[0-9]+") && pair[1].length() == 9) {
                    return "valid";
                } else return "invalid";
            case "cid":
                return "valid";
            default:
                return "invalid";
        }
    }

    private String checkField(String string) {
        switch (string) {
            case "byr":
            case "iyr":
            case "eyr":
            case "hgt":
            case "hcl":
            case "ecl":
            case "pid":
            case "cid":
                return string;
            default:
                return "?";
        }
    }

    private void readFile() {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                if (!nextLine.isEmpty()) {
                    lines.add(nextLine);
                } else {
                    String[] passportEntries = new String[lines.size() + 1];
                    lines.toArray(passportEntries);
                    passportEntries[passportEntries.length - 1] = "?";
                    passports.add(passportEntries);
                    lines.clear();
                }
            }
            String[] lastEntry = new String[lines.size() + 1];
            lines.toArray(lastEntry);
            lastEntry[lastEntry.length - 1] = "?";
            passports.add(lastEntry);
            lines.clear();
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
    }
}
