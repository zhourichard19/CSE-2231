import components.program.Program1;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;

/**
 * Homework 26
 *
 * @author Richard Zhou
 */
public final class Homework25 extends Program1 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Homework25() {
        super();
    }

    /**
     * Definition of whitespace separators.
     */
    private static final String SEPARATORS = " \t\n\r";

    /**
     * /** Returns the first "word" (maximal length string of characters not in
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

        Set<Character> charSet = new Set1L<Character>();

        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!charSet.contains(c)) {
                charSet.add(c);
            }
        }

        int last = position;
        boolean isTrue = charSet.contains(text.charAt(last));
        boolean doesContain = charSet.contains(text.charAt(last));

        while (last < text.length() && doesContain == isTrue) {
            last++;
        }

    }

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

        Set<Character> charSet = new Set1L<Character>();

        for (int i = 0; i < SEPARATORS.length(); i++) {
            char c = SEPARATORS.charAt(i);
            if (!charSet.contains(c)) {
                charSet.add(c);
            }
        }

        Queue<String> tokenQueue = new Queue1L<String>();
        while (!in.atEOS()) {
            int pos = 0;
            String line = in.nextLine();
            while (pos < line.length()) {
                String token = nextWordOrSeparator(line, pos);
                if (!charSet.contains(line.charAt(pos))) {
                    tokenQueue.enqueue(token);
                }
                pos += token.length();
            }
        }
        return tokenQueue;
    }
}