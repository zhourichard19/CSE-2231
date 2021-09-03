import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Project to count the number of times a word appears in a statement
 *
 * @author Richard Zhou
 *
 *         </pre>
 */
public final class WordCount {

    /**
     * Private no-argument constructor to prevent instantiation of this utility
     * class.
     */
    private WordCount() {

    }

    /**
     * Compare {@code String}s in lexicographic order
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }

    /**
     * Makes an HTML page for the table of the word and how many time it was
     * used
     *
     * @param outFile
     *            The Output file for saving the HTML
     * @param words
     *            The map with the words and their occurrence 
     * @param keyQueue
     *            the Queue having all of the words Keys
     * @param inFile
     *            the input text file read read by our code
     * @ensures <pre>
     * a table is made with the word and their occurrence form the map and Queue
     * </pre>
     *
     */
    public static void tableDesign(SimpleWriter outFile,
            Map<String, Integer> words, Queue<String> keyQueue,
            SimpleReader inFile) {
        assert words != null : "Violation of: words is not null";
        assert keyQueue != null : "Violation of: keyQueue is not null";
        assert outFile.isOpen() : "Violation of: outFile is open";
        assert inFile.isOpen() : "Violation of: inFile is open";

        //creates header
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println(
                "<title>Words counted in " + inFile.name() + "</title>");

        outFile.println("<body>");
        outFile.println("<h2>Words Counted in " + inFile.name() + "</h2>");
        outFile.println("<hr />");
        //starts  to make the table
        outFile.println("<table border=\"1\">");
        outFile.println("<tr>");
        outFile.println("<th>Words</th>");
        outFile.println("<th>Counts</th>");
        outFile.println("</tr>");
        //puts the word and the count into the table
        while (words.iterator().hasNext()) {
            Pair<String, Integer> line = words.remove(keyQueue.dequeue());
            outFile.println("<tr>");
            outFile.println("<td>" + line.key() + "</td>");
            outFile.println("<td>" + line.value() + "</td>");
            outFile.println("</tr>");
        }
        //creates the footer
        outFile.println("</table>");
        outFile.println("</body>");
        outFile.println("</html>");

    }

    /**
     * Takes a string and looks for the first word starting at the position The
     * word is determined by if there is a separator (periods, spaces, commas,
     * etc). Once the method finds a separator, it determines the word is the
     * starting position until right before the separator The word is returned
     * If the position is at a separator, the separator alone is returned
     *
     * @param text
     *            String that is to have words examined
     * @param position
     *            Staring position to check for word
     * @param separators
     *            A set of designated separators
     * @return Word or separator if the position starts at one
     * @requires text is not empty separators is not empty position is less than
     *           the length of text
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        //empty string to be returned
        String wordOrSeparator = "";

        //sets the beginning position which will not change
        int pos = position;

        //if the character is not a separator
        if (!separators.contains(text.charAt(position))) {
            //takes the char at the position and saves it and then moves to the
            //next index
            while (pos < text.length()
                    && !separators.contains(text.charAt(pos))) {
                pos++;
            }
            //when their is a separator it compares to the end of the word to
            //know hen to end
        } else {
            while (pos < text.length()
                    && separators.contains(text.charAt(pos))) {
                pos++;
            }
        }

        //gets the substring which is the word that is needed to be accounted
        //for
        wordOrSeparator = text.substring(position, pos);
        //returns the word that is separated
        return wordOrSeparator;

    }

    /**
     * Reads through the String file and puts the words and counts inside of a
     * map
     *
     * @param inFile
     *            The input text File
     * @param countMap
     *            the map that has all of the words and the counts
     * @ensures <pre>
     * that all of the words in the input file are added to the word map
     * </pre>
     */
    public static void setWords(SimpleReader inFile,
            Map<String, Integer> countMap) {

        assert inFile.isOpen() : "Violation of : inFile is open";
        assert countMap != null : "Violation of: words is not null";

        countMap.clear();
        //a set for punctuation and spaces
        Set<Character> separators = new Set1L<>();

        //adds the needed punctuation to the punctation set
        separators.add('.');
        separators.add(',');
        separators.add(' ');
        separators.add(':');
        separators.add('!');
        separators.add('?');

        //sets the first initial position
        int pos = 0;

        //checks to make sure the file still has words that are next or within
        //it
        while (!inFile.atEOS()) {
            //the first line to be read in the file
            String line = inFile.nextLine();
            pos = 0;
            while (pos < line.length()) {
                //reads every word and separates them from by spaces and
                //punctuation
                String word = nextWordOrSeparator(line, pos, separators);
                //gets the separated word
                //checks to see if the map already has the word
                if (!separators.contains(word.charAt(0))) {
                    if (!countMap.hasKey(word)) {
                        //if the map does not have the word, then that word gets
                        //made a new column and counted at 1
                        countMap.add(word, 1);
                    } else {
                        //if the map has the word, then val which is the value
                        //gets added one more
                        int val = countMap.value(word);
                        val++;
                        countMap.replaceValue(word, val);
                    }
                }
                //moves on to the next position by one
                pos += word.length();
            }

        }

    }

    /**
     * Goes through the word map and sets it from a-z order
     *
     * @param words
     *            The map with the words and the counts
     * @param order
     *            The comparator which sort the wird map from a-z
     * @param keyQueue
     *            the queue that contains all of the words
     * @ensures <pre>
     * the word map will be sorted from A-Z
     * </pre>
     *
     */

    public static void sortMap(Map<String, Integer> wordCount,
            Comparator<String> order, Queue<String> sorter) {
        assert wordCount != null : "Violation of : words is not null";
        assert order != null : "Violation of : words is not null";
        assert sorter != null : "Violation of : words is not null";

        //creates a temporary map
        Map<String, Integer> tempMap = wordCount.newInstance();
        //creates a temporary queue
        Queue<String> tempQueue = sorter.newInstance();
        sorter.clear();

        //Goes through all of the word elements
        while (wordCount.iterator().hasNext()) {
            // removes all elements until one remains
            Pair<String, Integer> tempPair = wordCount.removeAny();

            //puts the values in the map
            tempMap.add(tempPair.key(), tempPair.value());
            //puts the values in queue
            sorter.enqueue(tempPair.key());
        }

        //sorts the queue in order
        sorter.sort(order);

        //goes through every order and makes the queue and map in alphabetical order
        while (sorter.iterator().hasNext()) {
            String tempKey = sorter.dequeue();
            Pair<String, Integer> orderedPair = tempMap.remove(tempKey);
            wordCount.add(orderedPair.key(), orderedPair.value());
            tempQueue.enqueue(tempKey);
        }
        sorter.transferFrom(tempQueue);

    }

    public static void main(String[] args) {
        SimpleWriter userOut = new SimpleWriter1L();
        SimpleReader userIn = new SimpleReader1L();

        //sets up the needed map, Queue, and comparators to use the code
        Map<String, Integer> countMap = new Map1L<String, Integer>();
        Comparator<String> comp = new StringLT();
        Queue<String> stringQueue = new Queue1L<String>();

        //asks the use for the file name
        userOut.println("Input File Name:");
        String input = userIn.nextLine();
        SimpleReader in = new SimpleReader1L(input);

        //asks the user for the output file name
        userOut.println("Enter folder where output files will be saved: ");
        String output = userIn.nextLine();
        SimpleWriter out = new SimpleWriter1L(output + "/wordCount.html");

        setWords(in, countMap);
        sortMap(countMap, comp, stringQueue);
        tableDesign(out, countMap, stringQueue, in);

        //closes everything
        userIn.close();
        userOut.close();
        in.close();
        out.close();

    }

}