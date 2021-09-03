import components.map.Map;
import components.program.Program1;
import components.sequence.Sequence;
import components.statement.Statement;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework31 extends Program1 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Homework31() {
        super();
    }

    /**
     * Generates the sequence of virtual machine instructions ("byte codes")
     * corresponding to {@code s} and appends it at the end of {@code cp}.
     *
     * @param s
     *            the {@code Statement} for which to generate code
     * @param context
     *            the {@code Context} in which to find user defined instructions
     * @param cp
     *            the {@code Sequence} containing the generated code
     * @updates cp
     * @ensures <pre>
     * if [all instructions called in s are either primitive or
     *     defined in context]  and
     *    [context does not include any calling cycles, i.e., recursion] then
     *  cp = #cp * [sequence of virtual machine "byte codes" corresponding to s]
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void generateCodeForStatement(Statement s,
            Map<String, Statement> context, Sequence<Integer> cp) {

        final int dummy = 0;

        switch (s.kind()) {
            case BLOCK: {

                Statement child = s.newInstance();
                for (int index = 0; index < s.lengthOfBlock(); index++) {
                    child = s.removeFromBlock(index);
                    generateCodeForStatement(child, context, cp);
                    s.addToBlock(index, child);
                }
            }
            case IF: {
                Statement b = s.newInstance();
                Condition c = s.disassembleIf(b);
                cp.add(cp.length(), conditionalJump(c).byteCode());
                int jump = cp.length();
                cp.add(cp.length(), dummy);
                generateCodeForStatement(b, context, cp);
                cp.replaceEntry(jump, cp.length());
                s.assembleIf(c, b);
                break;
            }
            case IF_ELSE: {

                Statement bIf = s.newInstance();
                Statement bElse = s.newInstance();
                Condition c = s.disassembleIfElse(bIf, bElse);
                cp.add(cp.length(), conditionalJump(c).byteCode());
                int condJump = cp.length();
                cp.add(cp.length(), dummy);
                generateCodeForStatement(bIf, context, cp);
                cp.add(cp.length(), Instruction.valueOf("JUMP").byteCode());
                int jump = cp.length();
                cp.add(cp.length(), dummy);
                cp.replaceEntry(condJump, cp.length());
                generateCodeForStatement(bElse, context, cp);
                cp.replaceEntry(jump, cp.length());
                s.assembleIfElse(c, bIf, bElse);
                break;
            }
            case WHILE: {

                Statement b = s.newInstance();
                Condition c = s.disassembleWhile(b);
                int condJump = cp.length();
                cp.add(cp.length(), conditionalJump(c).byteCode());
                int jump = cp.length();
                cp.add(cp.length(), dummy);
                generateCodeForStatement(b, context, cp);
                cp.add(cp.length(), Instruction.valueOf("JUMP").byteCode());
                cp.add(cp.length(), condJump);
                s.assembleWhile(c, b);
                cp.replaceEntry(jump, cp.length());
                break;
            }
            case CALL: {

                String name = s.disassembleCall();
                if (context.hasKey(name)) {
                    generateCodeForStatement(context.value(name),
                            context.newInstance(), cp);
                } else {
                    name = name.toUpperCase();
                    cp.add(cp.length(), Instruction.valueOf(name).byteCode());
                    name = name.toLowerCase();
                }
                s.assembleCall(name);
                break;
            }
        }
    }
}