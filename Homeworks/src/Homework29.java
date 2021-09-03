import components.program.Program1;
import components.queue.Queue;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework29 extends Program1 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Homework29() {
        super();
    }

    /**
     * Evaluates a Boolean expression and returns its value.
     *
     * @param tokens
     *            the {@code Queue<String>} that starts with a bool-expr string
     * @return value of the expression
     * @updates tokens
     * @requires [a bool-expr string is a prefix of tokens]
     * @ensures
     *
     *          <pre>
     * valueOfBoolExpr =
     *   [value of longest bool-expr string at start of #tokens]  and
     * #tokens = [longest bool-expr string at start of #tokens] * tokens
     *          </pre>
     */
    public static boolean valueOfBoolExpr(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";

        boolean value = true;
        while (tokens.length() != 0) {
            switch (tokens.dequeue()) {
                case "T": {
                    value = true;
                    break;
                }
                case "F": {
                    value = false;
                    break;
                }
                case "NOT": {
                    value = !valueOfBoolExpr(tokens);
                    break;
                }
                case "(": {
                    value = valueOfBoolExpr(tokens);
                    break;
                }
                case ")": {
                    break;
                }
                case "AND": {
                    value &= valueOfBoolExpr(tokens);
                    break;
                }
                case "OR": {
                    value |= valueOfBoolExpr(tokens);
                    break;
                }

                default:
                    break;
            }
        }

        return value;
    }
}