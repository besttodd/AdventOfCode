package besttod.com;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    public ReadFile() {
    }

    public List<String> readFile(String filePath) {
        File inputFile = new File(filePath);
        List<String> input = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                input.add(nextLine);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("File empty or incompatible.");
        }
        return input;
    }
}
