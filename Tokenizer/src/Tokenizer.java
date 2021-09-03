import static components.utilities.Tokenizer.isCondition;
import static components.utilities.Tokenizer.isIdentifier;
import static components.utilities.Tokenizer.isKeyword;

import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * {@code Tokenizer} utility class with methods to tokenize an input stream and
 * to perform various checks on tokens.
 */
public final class Tokenizer {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Definition of whitespace separators.
     */
    private static final String SEPARATORS = " \t\n\r";

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Tokenizer() {
    }

    /**
     * Returns the token "kind" corresponding to the given {@code token}.
     *
     * @param token
     *            the given token
     * @return the "kind" of the given token
     * @ensures tokenKind = ["kind" of the given token]
     */
    private static String tokenKind(String token) {
        assert token != null : "Violation of: token is not null";
        String kind = "ERROR     ";
        if (isKeyword(token)) {
            kind = "KEYWORD   ";
        } else if (isCondition(token)) {
            kind = "CONDITION ";
        } else if (isIdentifier(token)) {
            kind = "IDENTIFIER";
        }
        return kind;
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        /*
         * Puts the separator characters in a set, all the separators are unique
         * and to check if something is a separator just invoke .contains()
         */
        String result = "";
        Set<String> separatorSet = new Set1L<>();
        int x = 0;
        while(x < SEPARATORS.length()) {
            /*
             * Case 1: the separator is \t. In this case the separator is two chars
             * so you add to the string twice. just check for "\"
             *
             * Case 2: the separator is one char. just do charAt once and add it to the set
             */
            String s = SEPARATORS.charAt(x)+"";
            if(s.equals("\\")){
                x++;
                s += SEPARATORS.charAt(x);
            }
            separatorSet.add(s);
            x++;
        }
        boolean isSeparator = false;
        while(position < text.length() && !isSeparator ) {
            /*
             * Case 1: if the token is a separator so is then boolean true
             * to stop the loop
             *
             * Case 2: the token is not a separator so the current string just gets
             * added to result. x is incremented
             */
            String currentToken = text.charAt(position)+"";
            if(separatorSet.contains(currentToken)) {
                isSeparator = true;
            } else {
                result += currentToken;
                position++;
            }
        }
        return result;
    }

    /*
     * Public members ---------------------------------------------------------
     */

    /**
     * Token to mark the end of the input. This token cannot come from the input
     * stream because it contains whitespace.
     */
    public static final String END_OF_INPUT = "### END OF INPUT ###";

    /**
     * Tokenizes the entire input getting rid of all whitespace separators and
     * returning the non-separator tokens in a {@code Queue<String>}.
     *
     * @param in
     *            the input stream
     * @return the queue of tokens
     * @requires in.is_open
     * @ensures <pre>
     * tokens =
     *   [the non-whitespace tokens in #in.content] * <END_OF_INPUT>  and
     * in.content = <>
     * </pre>
     */
    public static Queue<String> tokens(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";

        Queue<String> result = new Queue1L<>();
        //Pulls out one line at a time and enqueues each non separator term
        while(!in.atEOS()) {
            String currentLine = in.nextLine();
            int position = 0;
            while(position < currentLine.length()) {
                /*
                 * Case 1: a word is returned, in this case the position just needs
                 * to be increased by the word length and word needs to be enqueued
                 *
                 * Case 2: a separator is returned
                 */
                String token = nextWordOrSeparator(currentLine, position);

                if(token.length() == 0) {

                    position++;
                } else {
                    position += token.length();
                    if(SEPARATORS.indexOf(token) == -1) {
                        result.enqueue(token);
                    }
                }

            }
        }
        result.enqueue(END_OF_INPUT);
        in.clear();
        return result;
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter input file name: ");
        String fileName = in.nextLine();
        /*
         * Tokenize input with Tokenizer implementation from library.
         */
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> q1 = components.utilities.Tokenizer.tokens(file);
        file.close();
        /*
         * Tokenize input with Tokenizer implementation under test.
         */
        file = new SimpleReader1L(fileName);
        Queue<String> q2 = Tokenizer.tokens(file);
        file.close();
        /*
         * Check that the two Queues are equal.
         */
        out.println();
        if (q2.equals(q1)) {
            out.println("Input appears to have been tokenized correctly.");
        } else {
            out.println("Error: input tokens are not correct.");
        }
        out.println();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        SimpleWriter tOut = new SimpleWriter1L("data/expected-output.txt");
        for (String token : q1) {
            tOut.println(tokenKind(token) + ": <" + token + ">");
        }
        tOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        tOut = new SimpleWriter1L("data/actual-output.txt");
        for (String token : q2) {
            tOut.println(tokenKind(token) + ": <" + token + ">");
        }
        tOut.close();

        in.close();
        out.close();
    }

}
