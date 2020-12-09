package besttod.com;

import java.io.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

//Day 2: Part 1 - Each line gives the password policy and then the password.
//                The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid.
//                For example, 1-3 a: means that the password must contain 'a' at least 1 time and at most 3 times.
//       Part 2 - Each policy actually describes two positions in the password,
//                where 1 means the first character, 2 means the second character, and so on.
//                (No concept of "index zero"!)
//                Exactly one of these positions must contain the given letter.
//                Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

public class Day2 {
    private final static String FILEPATH = "C:\\Users\\bestt\\Coding\\AdventOfCode\\day2_input.txt";

    private static List<String> input = new ArrayList<>();
    private int bound2;
    private int bound1;
    private char condition;
    private String password;

    public Day2() {
        int correctPWDsSleds = 0;
        int correctPWDsToboggan = 0;

        readFile();

        for (String s : input) {
            splitLine(s);
            if (checkPasswordSleds(password)) {
                correctPWDsSleds++;
            }
            if (checkPasswordToboggan(password)) {
                correctPWDsToboggan++;
            }
        }

        System.out.println("Number of valid SLED passwords: " + correctPWDsSleds);
        System.out.println("Number of valid TOBOGGAN passwords: " + correctPWDsToboggan);
    }

    private boolean checkPasswordToboggan(String password) {
        if ((password.charAt(bound1 - 1) == condition) ^ (password.charAt(bound2 - 1) == condition)) {
            System.out.println("Acceptable Password.\n");
            return true;
        } else {
            System.out.println("Invalid Password!!\n");
            return false;
        }
    }

    private boolean checkPasswordSleds(String password) {
        int count = 0;

        CharacterIterator charIt = new StringCharacterIterator(password);

        while ((count < (bound2 + 1)) && (charIt.current() != CharacterIterator.DONE)) {
            if (charIt.current() == condition) {
                count++;
            }
            charIt.next();
        }

        if (count < bound2 + 1 && count > bound1 - 1) {
            System.out.println("Acceptable Password.\n");
            return true;
        } else {
            System.out.println("Invalid Password!!\n");
            return false;
        }
    }

    private void splitLine(String string) {
        String[] result = string.split("-");
        bound1 = Integer.parseInt(result[0]);
        result = result[1].split(" ");
        bound2 = Integer.parseInt(result[0]);
        condition = result[1].charAt(0);
        password = result[2];
        System.out.println("Range: " + bound1 + "-" + bound2 + "\nChar: " + condition + "\nPassword: " + password);
    }

    private void readFile() {
        File inputFile = new File(FILEPATH);

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                input.add(nextLine);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
    }
}
