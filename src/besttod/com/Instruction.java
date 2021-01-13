package besttod.com;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {
    private static final Pattern VALID_PATTERN = Pattern.compile("[a-z]+|[0-9]+");

    private String operation;
    private int argument;

    public Instruction(String input) {
        List<String> line = parse(input);              //nop +2
        operation = line.get(0);                       //nop
        String value = line.get(2) + line.get(1);      //"+2"
        argument = Integer.parseInt(value);            //"2"
    }

    private List<String> parse(String toParse) {
        List<String> chunks = new LinkedList<>();
        Matcher matcher = VALID_PATTERN.matcher(toParse);
        while (matcher.find()) {
            chunks.add(matcher.group());
        }
        if (toParse.contains("-")) {
            chunks.add("-");
        } else chunks.add("+");
        return chunks;
    }

    public String getOperation() {
        return operation;
    }

    public int getArgument() {
        return argument;
    }

    public void swapOperation() {
        if (operation.equals("jmp")) {
            operation = "nop";
        } else {
            operation = "jmp";
        }
    }
}