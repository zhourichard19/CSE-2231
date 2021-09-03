import components.program.Program1;
import components.queue.Queue;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Quiz45 extends Program1 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Quiz45() {
        super();
    }

    /**
     * Evaluates an expression and returns its value.
     *
     * @param source
     *            the {@code StringBuilder} that starts with an expr string
     * @return value of the expression
     * @updates source
     * @requires <pre>
     * [an expr string is a proper prefix of source, and the longest
     * such, s, concatenated with the character following s, is not a prefix
     * of any expr string]
     * </pre>
     * @ensures <pre>
     * valueOfExpr =
     *   [value of longest expr string at start of #source]  and
     * #source = [longest expr string at start of #source] * source
     * </pre>
     */
    public static int valueOfExpr(Queue<String> tokens) {

        String numbers = "0123456789";
        String token = tokens.dequeue();
        int num = Integer.parseInt(token);
        while (tokens.length() > 0) {
            if (token == "+") {
                num += valueOfExpr(tokens);
            } else if (token == "-") {
                num -= valueOfExpr(tokens);
            } else if (token == "*") {
                num *= valueOfExpr(tokens);
            } else if (token == "/") {
                num /= valueOfExpr(tokens);
            }
        }
        return num;
    }

}