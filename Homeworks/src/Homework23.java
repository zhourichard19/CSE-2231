import components.statement.Statement;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class Homework23 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Homework23() {
        // no code needed here
    }

    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
            case BLOCK: {

                for (int i = 0; i < s.lengthOfBlock(); i++) {
                    Statement child = s.removeFromBlock(i);
                    simplifyIfElse(child);
                    s.addToBlock(i, child);
                }
                break;
            }
            case IF: {

                Statement child = s.newInstance();
                Statement.Condition c = s.disassembleIf(child);
                simplifyIfElse(child);
                s.assembleIf(c, child);
                break;
            }
            case IF_ELSE: {

                Statement childIf = s.newInstance();
                Statement childElse = s.newInstance();
                Statement.Condition c = s.disassembleIfElse(childIf, childElse);
                switch (c.name()) {
                    case "NEXT_IS_NOT_FRIEND": {
                        c = c.NEXT_IS_FRIEND;
                        simplifyIfElse(childIf);
                        simplifyIfElse(childElse);
                        s.assembleIfElse(c, childElse, childIf);
                        break;
                    }
                    case "NEXT_IS_NOT_ENEMY": {
                        c = c.NEXT_IS_ENEMY;
                        simplifyIfElse(childIf);
                        simplifyIfElse(childElse);
                        s.assembleIfElse(c, childElse, childIf);
                        break;

                    }

                    case "NEXT_IS_NOT_EMPTY": {
                        c = c.NEXT_IS_EMPTY;
                        simplifyIfElse(childIf);
                        simplifyIfElse(childElse);
                        s.assembleIfElse(c, childElse, childIf);
                        break;
                    }
                    case "NEXT_IS_NOT_WALL": {
                        c = c.NEXT_IS_WALL;
                        simplifyIfElse(childIf);
                        simplifyIfElse(childElse);
                        s.assembleIfElse(c, childElse, childIf);
                        break;

                    }
                }
            }
            case WHILE: {

                Statement child = s.newInstance();
                Statement.Condition c = s.disassembleWhile(child);
                simplifyIfElse(child);
                s.assembleWhile(c, child);
                break;
            }
            case CALL: {
                // nothing to do here...can you explain why?
                break;
            }
            default: {
                // this will never happen...can you explain why?
                break;
            }
        }
    }

}
