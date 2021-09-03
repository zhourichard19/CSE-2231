import java.util.Comparator;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;

/**
 * Program to sort lines from an input file in lexicographic order by using
 * insertion sort on {@code Queue<String>}.
 *
 * @author Paolo Bucci
 */
public final class QueueSortMain {

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private QueueSortMain() {
        // no code needed here
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Get input file name and open input stream
         */
        out.print("Enter an input file name: ");
        String fileName = in.nextLine();
        SimpleReader file = new SimpleReader1L(fileName);

        /*
         * Sort lines into non-decreasing lexicographic order
         */
        Comparator<String> cs = new StringLT();
        SortingMachine<String> sorter = new SortingMachine4<>(cs);

        /*
         * Get lines from input
         */

        int count = 0;
        Queue<String> q = new Queue1LSort4<>();
        while (!file.atEOS()) {
            String str = file.nextLine();
            sorter.add(str);
            count++;
        }
        file.close();

        sorter.changeToExtractionMode();

        /*
         * Output the number of lines to be sorted
         */
        out.println();
        out.println("Number of lines in input: " + count);

        /*
         * Output lines in sorted order
         */
        count = 0;
        out.println();
        while (sorter.size() > 0) {
            String str = sorter.removeFirst();
            out.println(str);
            count++;
        }

        /*
         * Output the number of lines sorted
         */
        out.println();
        out.println("Number of lines in output: " + count);

        in.close();
        out.close();
    }

}
