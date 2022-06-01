import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 * Utility to parse the text files containing the catalogues
 */
public class Parser {
    public static void parse(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            Stack<String> context = new Stack<>();

            while(sc.hasNextLine()) {
                String input = sc.nextLine();
            }

            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
