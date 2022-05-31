import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A class that contains a lot of useful methods for the rest of the application to use
 */
public class Utils {
    /**
     * @param initial initial message to print to the user (choices)
     * @param msg input prompt to print to the user
     * @param errMsg the error message to print to the user in case the input is invalid
     * @param range comma-seperated strings that make up the list of correct values
     * @return a valid input
     */
    public static String checkInput(String initial, String msg, String errMsg, String range) {
        System.out.println(initial + msg);
        String input;
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        try {
            input = sc.next();
        } catch (InputMismatchException ignored) {
            input = null;
        }

        while (!contains(range.split(","), input)) {
            System.out.print(errMsg + "\n" + msg);
            try {
                input = sc.next();
            } catch (InputMismatchException e) {
                input = null;
            }
        }

        System.out.println();

        return input;
    }

    /**
     *
     * @param arr the values to search in
     * @param tar the target to search for
     * @return whether the @param arr (array) contains the string @param tar
     */
    public static boolean contains(String[] arr, String tar) {
        Arrays.sort(arr);

        return Arrays.binarySearch(arr, tar) >= 0;
    }

    /**
     * @return the current date with format: dd-MM-yyyy
     */
    public static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ld = LocalDate.now();

        return dtf.format(ld);
    }

    /**
     * @param msg the message prompt to print to the user
     * @param errMsg the error message to print to the user if input is invalid
     * @return the string representation of the date
     */
    public static String checkDate(String msg, String errMsg) {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);

        System.out.println(msg);
        String date = sc.next().trim();
        String[] parts = date.split("-");

        while (
                parts.length != 3 ||

                parts[0].length() != 2 ||
                parts[1].length() != 2 ||
                parts[2].length() != 4 ||

                !(Integer.parseInt(parts[0]) <= 31) ||
                !(Integer.parseInt(parts[1]) <= 12) ||

                        isNumeric(parts[0]) ||
                        isNumeric(parts[1]) ||
                        isNumeric(parts[2]) ||

                !isDateValid(date)
        ) {
            System.out.println(errMsg + "\n" + msg);
            date = sc.next().trim();
            parts = date.split("-");
        }

        return date;
    }

    /**
     * @param string the string to check
     * @return whether the string only contains numeric characters
     */
    public static boolean isNumeric(String string) {
        return !string.chars().allMatch(Character::isDigit);
    }

    /**
     * @param date the string representation of the date to check
     * @return whether the string representation of the date is valid
     */
    public static boolean isDateValid(String date) {
        if(Arrays.equals(date.split("-"), getDate().split("-"))) return true;

        String[] inpParts = date.split("-");
        String[] currParts = getDate().split("-");

        for(int i=inpParts.length - 1; i >= 0; i--)
            if(Integer.parseInt(inpParts[i]) > Integer.parseInt(currParts[i])) return true;

        return false;
    }
}
