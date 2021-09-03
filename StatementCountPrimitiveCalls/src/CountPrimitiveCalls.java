import components.statement.Statement;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     *
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement child = s.removeFromBlock(i);
                    count += countOfPrimitiveCalls(child);
                    s.addToBlock(i, child);
                }

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement child = s.newInstance();

                Statement.Condition c = s.disassembleIf(child);
                count = countOfPrimitiveCalls(child);
                s.assembleIf(c, child);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement child = s.newInstance();
                Statement childElse = s.newInstance();

                Statement.Condition c = s.disassembleIfElse(child, childElse);
                count = countOfPrimitiveCalls(child)
                        + countOfPrimitiveCalls(childElse);
                s.assembleIfElse(c, child, childElse);

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement child = s.newInstance();

                Statement.Condition c = s.disassembleWhile(child);
                count = countOfPrimitiveCalls(child);
                s.assembleWhile(c, child);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or
                 */

                String childName = s.disassembleCall();
                if (childName.equals("turnright") || childName.equals("move")
                        || childName.equals("infect")
                        || childName.equals("turnleft")
                        || childName.equals("skip")) {
                    count++;
                }
                s.assembleCall(childName);
                break;
            }
            default: {
                // this will never happen...can you explain why?
                //all of the possibilities that the code could be have been checked
                //so this will never happen
                break;
            }
        }
        return count;
    }

}