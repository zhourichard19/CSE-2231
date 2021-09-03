import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework36 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Homework36() {
        // no code needed here
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        BufferedReader input;
        PrintWriter output;

        //output
        try {
            output = new PrintWriter(new FileWriter(args[1]));
        } catch (IOException e) {
            System.err.println("Error opening output file");
            return;
        }

        //input
        try {
            input = new BufferedReader(new FileReader(args[0]));
        } catch (IOException e) {
            System.err.println("Error opening file");
            output.close();
            return;
        }

        try {
            String s = input.readLine();
            while (s != null) {
                output.println(s);
                s = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
        try {
            input.close();
        } catch (IOException e) {
            System.err.println("Error clsoing from file");
        }
        output.close();
    }
}
