import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

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
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            //sets the first and second words that  will be compared
            String word1 = o1.key();
            String word2 = o2.key();
            return word1.compareTo(word2);
        }
    }

    /**
     * Default constructor--private to prevent instantiation. sets a comparator
     * for the words and sets them in order of value them
     */

    private static class numericalOrder
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> o1,
                Map.Pair<String, Integer> o2) {
            //sets the values that the comparator will compare
            Integer value1 = o1.value();
            Integer value2 = o2.value();
            return value2.compareTo(value1);
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
    private static SortingMachine<Map.Pair<String, Integer>> alphabeticalSortMachine(
            Map<String, Integer> wordCount, int num) {
        if (num > wordCount.size()) {
            num = wordCount.size();
        }
        //sets the comparators for the counts and the alphabets
        Comparator<Map.Pair<String, Integer>> countOrder = new numericalOrder();
        Comparator<Map.Pair<String, Integer>> keyOrder = new alphabeticalOrder();

        //gets a sorting machine for the counting of the alphabet and the value
        SortingMachine<Map.Pair<String, Integer>> countSort = new SortingMachine1L<>(
                countOrder);
        SortingMachine<Map.Pair<String, Integer>> wordSort = new SortingMachine1L<>(
                keyOrder);

        //sets the int to look thru the count and add it to the new map pair
        int i = 0;
        for (Map.Pair<String, Integer> pair : wordCount) {
            countSort.add(pair);
        }
        //changes the sorting machine to extraction mode
        countSort.changeToExtractionMode();
        for (Map.Pair<String, Integer> pair : countSort) {
            //checks to see if there ismoe left to sort
            if (i < num) {
                wordSort.add(pair);
                i++;
            }
        }
        wordSort.changeToExtractionMode();
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
     * @param out
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
    public static void generateHTML(SimpleWriter out,
            SortingMachine<Map.Pair<String, Integer>> alphabetical,
            String fileName) {
        //makes a sorting machine in order to compare and organize by alphabet
        SortingMachine<Map.Pair<String, Integer>> temp = alphabetical
                .newInstance();
        //sets a comparator to organize the map by occurences
        Comparator<Map.Pair<String, Integer>> countOrder = new numericalOrder();
        //sorting machine to sort the order by occurences
        SortingMachine<Map.Pair<String, Integer>> countSort = new SortingMachine1L<>(
                countOrder);

        //checks to go through all of the alphabetically sorted map to a count
        //sorted map
        for (Map.Pair<String, Integer> pair : alphabetical) {
            countSort.add(pair);
        }
        countSort.changeToExtractionMode();

        //removes the first one of the count sortedmap
        Map.Pair<String, Integer> pair = countSort.removeFirst();

        //sets the max because the map is sorted by mostto least occurences
        int max = pair.value();

        int least = 0;
        //goes until the least amount of occurences is found
        while (countSort.size() > 0) {
            pair = countSort.removeFirst();
            least = pair.value();
        }

        //prints out the header of the html
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        //Include style sheet here?
        out.println("<head>");
        //sets the title oft  he html
        out.println("<title>" + "top " + alphabetical.size() + " words in "
                + fileName + "</title>");

        //links the styliing with css
        out.println(
                "<link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("<head>");
        out.println("<body>");

        //prints out a second header
        out.println("<h2>" + "top " + alphabetical.size() + " words in "
                + fileName + " </h2>");
        out.println("<hr>");
        out.println("<div class =\"cdiv\">");
        out.println("<p class =\"cbox>\">");
        //goes through every word and based off the amount of occurences that
        //the word has, the font size will  be bigger and bigger
        while (alphabetical.size() > 0) {

            //removes the first pair in the map
            Map.Pair<String, Integer> pair2 = alphabetical.removeFirst();
            //sets the size of the font based off of the occurrences of that word
            int sizeOfFont = fontSize(pair2.value(), max, least);
            //prints out the word with its respective fonts
            out.print("<span style=\"cursor:default\" class=\"f");
            out.print(sizeOfFont);
            out.print("\" title=\"count: ");
            out.println(pair2.value() + "\">" + pair2.key() + "</span>");
            temp.add(pair2);
        }
        temp.changeToExtractionMode();
        //resets all of the variables
        alphabetical.transferFrom(temp);
        //closes all of theheads
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

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
                    word = word.toLowerCase();
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
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // Ask for input file
        out.println("Enter a file: ");
        //String fileName = in.nextLine();
        String fileName = "data\\test1.txt";
        SimpleReader file = new SimpleReader1L(fileName);
        String input = fileName;

        //sets a writer for the file
        SimpleWriter fileOut = new SimpleWriter1L("data/output.html");
        //asks for the users response
        out.print("How many entries do you want to search?");
        String num = in.nextLine();
        //checks cases to make sure that the entry is a number
        if (Integer.parseInt(num) < 0) {
            out.println();
            out.print("Enter a positive integer");
            num = in.nextLine();
        }
        if (num.contentEquals(" ")) {
            out.println();
            out.print("Please enter a valid number");
            num = in.nextLine();
        }
        if (num.contains(".")) {
            out.println();
            out.print("Please enter a valid integer");
            num = in.nextLine();
        }
        int numEntries = Integer.parseInt(num);

        //sets a word count map that will be counted in the next methods
        Map<String, Integer> wordCount = new Map1L<String, Integer>();
        //goes through the set and counts the occurrences and puts them in a map
        setWords(file, wordCount);
        //sorts the map alphabetical
        SortingMachine<Map.Pair<String, Integer>> alphabetSort = alphabeticalSortMachine(
                wordCount, numEntries);

        //prints out the html of the map
        generateHTML(fileOut, alphabetSort, input);

        //closes all of the scanners
        in.close();
        out.close();
        fileOut.close();

    }

}
