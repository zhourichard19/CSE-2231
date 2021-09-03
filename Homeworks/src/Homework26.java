import components.program.Program;
import components.program.Program1;
import components.statement.Statement;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework26 extends Program1 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Homework26() {
        super();
    }

    /**
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.
     *
     * @param s
     *            the {@code Statement}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates s
     * @requires [newName is a valid IDENTIFIER]
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of instruction oldName
     *   is replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        switch (s.kind()) {
            case BLOCK: {
                int length = s.lengthOfBlock();
                for (int i = 0; i < length; i++) {
                    Statement child = s.removeFromBlock(i);
                    renameInstruction(child, oldName, newName);
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
                renameInstruction(child, oldName, newName);
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
                renameInstruction(child, oldName, newName);
                renameInstruction(childElse, oldName, newName);
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
                renameInstruction(child, oldName, newName);
                s.assembleWhile(c, child);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String childName = s.disassembleCall();
                if (childName.equals(oldName)) {
                    s.assembleCall(newName);
                } else {
                    s.assembleCall(childName);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * Refactors the given {@code Program} by renaming instruction
     * {@code oldName}, and every call to it, to {@code newName}. Everything
     * else is left unmodified.
     *
     * @param p
     *            the {@code Program}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates p
     * @requires <pre>
     * oldName is in DOMAIN(p.context)  and
     * [newName is a valid IDENTIFIER]  and
     * newName is not in DOMAIN(p.context)
     * </pre>
     * @ensures <pre>
     * p = [#p refactored so that instruction oldName and every call
     *   to it are replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {
        Map<String, Statement> c = p.newContext();
        Map<String, Statement> newContext = p.replaceContext(c);
        while (newContext.size() != 0) {
            Map.Pair<String, Statement> temp = newContext.removeAny();
            String key = temp.key();
            if (temp.key().equals(oldName)) {
                key = newName;
            }
            renameInstruction(temp.value(), oldName, newName);
            c.add(key, temp.value());
        }

        p.replaceContext(c);
        Statement b = p.newBody();
        Statement q = p.replaceBody(q);
        renameInstruction(q, oldName, newName);
        p.replaceBody(q);
    }
}