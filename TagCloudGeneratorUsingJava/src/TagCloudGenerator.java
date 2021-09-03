import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Program that generates a tag cloud from a given input text
 *
 * @author Daniel Wu, Ricky Zhou, Luke Wingert
 */
public final class TagCloudGenerator {

    /**
     * Default constructor--private to prevent instantiation. sets a comparator
     * for the words and alphabetizes them
     */

    private static class alphabeticalOrder
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            //sets the first and second words that  will be compared
            String word1 = o1.getKey();
            String word2 = o2.getKey();
            int result = word1.compareTo(word2);
            if (result == 0) {
                result = o2.getValue().compareTo(o1.getValue());
            }
            return result;
        }
    }

    /**
     * Default constructor--private to prevent instantiation. sets a comparator
     * for the words and sets them in order of value them
     */

    private static class numericalOrder
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            //sets the values that the comparator will compare
            Integer value1 = o1.getValue();
            Integer value2 = o2.getValue();
            int result = value2.compareTo(value1);
            if (result == 0) {
                o1.getKey().compareTo(o2.getKey());
            }
            return result;
        }
    }

    /**
     * Sorts the map into a sorting machine by alphabetical order
     *
     * @param wordCount
     *            the Map with all of the words and the appearances
     * @param num
     *            the number of SortingMachine after resize.
     * @return the resized SortingMachine with alphabetic order.
     * @requires n>0
     */
    private static PriorityQueue<Map.Entry<String, Integer>> alphabeticalSortMachine(
            Map<String, Integer> wordCount, int num) {
        if (num > wordCount.size()) {
            num = wordCount.size();
        }
        //sets the comparators for the counts and the alphabets
        Comparator<Map.Entry<String, Integer>> countOrder = new numericalOrder();
        Comparator<Map.Entry<String, Integer>> keyOrder = new alphabeticalOrder();

        //gets a sorting machine for the counting of the alphabet and the value
        PriorityQueue<Map.Entry<String, Integer>> countSort = new PriorityQueue<>(
                countOrder);
        PriorityQueue<Map.Entry<String, Integer>> wordSort = new PriorityQueue<>(
                keyOrder);

        //sets the int to look thru the count and add it to the new map pair
        int i = 0;
        for (Map.Entry<String, Integer> pair : wordCount.entrySet()) {
            countSort.add(pair);
        }

        for (Map.Entry<String, Integer> pair : countSort) {
            //checks to see if there is more left to sort
            if (i < num) {
                wordSort.add(pair);
                i++;
            }
        }
        return wordSort;
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
     * Makes an HTML page for the tagCloud of the word and how many time it was
     * used
     *
     * @param output
     *            The Output file for saving the HTML
     * @param alphabetical
     *            The map with the words and their occurrence count
     * @param fileName
     *            the file string input
     * @ensures <pre>
     * an html page is created for the tag cloud and their counts
     * </pre>
     *
     */
    public static void generateHTML(PrintWriter output,
            PriorityQueue<Map.Entry<String, Integer>> alphabetical,
            String fileName) {

        //makes a sorting machine in order to compare and organize by alphabet
        Comparator<Map.Entry<String, Integer>> countOrder = new numericalOrder();
        PriorityQueue<Map.Entry<String, Integer>> temp = new PriorityQueue<>(
                countOrder);

        temp.addAll(alphabetical);
        //sets a comparator to organize the map by occurences

        //sorting machine to sort the order by occurences
        PriorityQueue<Map.Entry<String, Integer>> countSort = new PriorityQueue<>(
                countOrder);

        //checks to go through all of the alphabetically sorted map to a count
        //sorted map
        for (Map.Entry<String, Integer> pair : alphabetical) {
            countSort.add(pair);
        }

        //removes the first one of the count sortedmap
        Map.Entry<String, Integer> pair = countSort.remove();

        //sets the max because the map is sorted by most to least occurences
        int max = pair.getValue();

        int min = 0;
        //goes until the max amount of occurences is found

        while (countSort.size() > 0) {
            pair = countSort.remove();
            min = pair.getValue();
        }

        //prints out the header of the html
        output.println("<!DOCTYPE html>");
        output.println("<html>");
        //Include style sheet here?
        output.println("<head>");
        //sets the title oft  he html
        output.println("<title>" + "top " + alphabetical.size() + " words in "
                + fileName + "</title>");

        //links the styliing with css
        output.println(
                "<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("<head>");
        output.println("<body>");

        //prints out a second header
        output.println("<h2>" + "top " + alphabetical.size() + " words in "
                + fileName + " </h2>");
        output.println("<hr>");
        output.println("<div class =\"cdiv\">");
        output.println("<p class =\"cbox>\">");
        //goes through every word and based off the amount of occurrences that
        //the word has, the font size will  be bigger and bigger
        while (alphabetical.size() > 0) {

            //removes the first pair in the map
            Map.Entry<String, Integer> pair2 = alphabetical.remove();
            //sets the size of the font based off of the occurrences of that word
            int sizeOfFont = fontSize(pair2.getValue(), max, min);
            //prints out the word with its respective fonts
            output.print("<span style=\"cursor:default\" class=\"f");
            output.print(sizeOfFont);
            output.print("\" title=\"count: ");
            output.println(
                    pair2.getValue() + "\">" + pair2.getKey() + "</span>");
            temp.add(pair2);
        }
        //resets all of the variables
        alphabetical.addAll(temp);
        //closes all of the heads
        output.println("</p>");
        output.println("</div>");
        output.println("</body>");
        output.println("</html>");

    }

    /**
     * sets the font of the word based off of how many occurrences there are
     *
     * @param word
     *            the count of the words
     * @param max
     *            the word with the max occurrence count value
     * @param min
     *            the word with the min occurrence count value
     * @ensures <pre>
     * font size will be  correct and updated based off the amount of occurrences
     * </pre>
     */
    public static int fontSize(int word, int max, int min) {

        //return (int)((double)(word - min) / (max - min)) * 37 + 11;
        double step1 = word - min;
        double step2 = max - min;
        double step3 = step1 / step2;
        double step4 = step3 * 37;
        int result = (int) step4 + 11;
        return result;

    }

    /**
     * Reads through the String file and puts the words and counts inside of a
     * map
     *
     * @param input
     *            The input text File
     * @param countMap
     *            the map that has all of the words and the counts
     * @ensures <pre>
     * that all of the words in the input file are added to the word map
     * </pre>
     */

    public static void setWords(BufferedReader input,

            Map<String, Integer> countMap) {

        assert input != null : "Violation of : inFile is open";
        assert countMap != null : "Violation of: words is not null";

        countMap.clear();
        //a set for punctuation and spaces
        Set<Character> separators = new HashSet<Character>();
        String next;
        String line;

        //adds the needed punctuation to the punctuation set
        separators.add('.');
        separators.add(',');
        separators.add(' ');
        separators.add(':');
        separators.add(';');
        separators.add('!');
        separators.add('?');
        separators.add('-');
        separators.add('(');
        separators.add(')');
        separators.add('[');
        separators.add(']');
        separators.add('"');
        separators.add('\t');
        separators.add('\n');
        separators.add('\r');
        separators.add('/');
        separators.add('\'');

        //sets the first initial position
        int pos = 0;

        //checks to make sure the file still has words that are next or within
        //it
        try {
            next = input.readLine();
        } catch (IOException e) {
            System.err.println("Error reading file");
            return;
        }
        while (next != null) {
            //the first line to be read in the file

            pos = 0;
            while (pos < next.length()) {
                //reads every word and separates them from by spaces and
                //punctuation
                String word = nextWordOrSeparator(next, pos, separators);
                //gets the separated word
                //checks to see if the map already has the word
                if (!separators.contains(word.charAt(0))) {
                    word = word.toLowerCase();
                    if (!countMap.containsKey(word)) {
                        //if the map does not have the word, then that word gets
                        //made a new column and counted at 1
                        countMap.put(word, 1);
                    } else {
                        //if the map has the word, then val which is the value
                        //gets added one more
                        int val = countMap.get(word);
                        val++;
                        countMap.replace(word, val);
                    }
                }
                //moves on to the next position by one
                pos += word.length();
            }
            try {
                next = input.readLine();
            } catch (IOException e) {
                System.err.println("Error reading file");
                return;
            }
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //Initializes the readers and string names of the files
        BufferedReader inFile = new BufferedReader(
                new InputStreamReader(System.in));
        BufferedReader input;
        PrintWriter output;
        String fileFolder;
        String fileName;
        int numWords = 0;

        //prompts the user out for a file name
        System.out.print("Enter a file: ");

        //Reads the file name, and if the file is unreadable then thros an error
        try {
            fileFolder = inFile.readLine();
        } catch (IOException e) {
            System.err.println("input text could not be read");
            return;
        }

        //sets a new reader that will read the file contents
        //throws error if it is an invalid file
        try {
            input = new BufferedReader(new FileReader(fileFolder));
        } catch (IOException e) {

            System.err.println("Error reading the fileFolder for an input");

            return;
        }

        //prompts the user for the name of an output file
        System.out.print("Name for the output file: ");

        //reads the name ofthe output file
        try {
            fileName = inFile.readLine();
        } catch (IOException e) {
            System.err.println("input string could not be parsed");
            return;
        }

        //creates a writer that will paste the output file into the file
        //the user just gave
        try {
            output = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            System.err.println("Error opening output file");
            return;
        }

        try {
            boolean isNeg = true;
            while (isNeg) {
                //prompts the user to enter a number as long as it is not negative
                System.out.print(
                        "Number of words to be included in the tag cloud ");
                //reads the input
                String numString = inFile.readLine();
                int checkNum = Integer.parseInt(numString);
                //checks to make sure that the number is not negastive or null
                if (numString != null && checkNum > 0) {
                    numWords = Integer.parseInt(numString);
                    //ends the while loop
                    isNeg = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Error identifying number to be inputted");
        }

        //ensures the user did not enter an empty number and
        //makes it into an integer

        //sets a word count map that will be counted in the next methods
        Map<String, Integer> wordCount = new TreeMap<String, Integer>();
        //goes through the set and counts the occurrences and puts them in a map
        setWords(input, wordCount);
        //sorts the map alphabetical
        PriorityQueue<Map.Entry<String, Integer>> alphabetSort = alphabeticalSortMachine(
                wordCount, numWords);

        //prints out the html of the map
        generateHTML(output, alphabetSort, fileFolder);

        //closes the scanners and writers for the files
        input.close();
        output.close();
        inFile.close();

    }

}
