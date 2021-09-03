
import components.simplewriter.SimpleWriter;
import components.statement.Statement;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework24 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Homework24() {
        super();
    }

    /**
     * Pretty prints {@code this} to the given stream {@code out} {@code offset}
     * spaces from the left margin using
     * {@link components.program.Program#INDENT_SIZE Program.INDENT_SIZE} spaces
     * for each indentation level.
     *
     * @param out
     *            the output stream
     * @param offset
     *            the number of spaces to be placed before every nonempty line
     *            of output; nonempty lines of output that are indented further
     *            will, of course, continue with even more spaces
     * @updates out.content
     * @requires out.is_open and 0 <= offset
     * @ensures <pre>
     * out.content =
     *   #out.content * [this pretty printed offset spaces from the left margin
     *                   using Program.INDENT_SIZE spaces for indentation]
     * </pre>
     */
    public void prettyPrint(SimpleWriter out, int offset) {
        switch (this.kind()) {
            case BLOCK: {
                for(int i =0; i< this.lengthOfBlock(); i++) {
                    Staement child = this.removeFromBlock();
                    child.prettyprint(out, offset);
                    this.addToBlock(i, subTree);
                }

                break;
            }
            case IF: {

                Statement child = s.newInstance();
                Statement.Condition c = s.disassembleIf(child);
                printSpaces(out, offSet);

                out.println("IF " + toStringCondition(c));
                child.prettyprint(out, offset + indent);

                for(int i = 0; i< offset; i++) {
                    out.print("")
                }
                out.println("END IF");
                s.assembleIf(c, child);
                break;
            }
            case IF_ELSE: {

                Statement childIf = this.newInstance();
                Statement childElse = this.newInstance();
                Condition c = this.disassembleIfElse(childIf,
                        childElse);
                printSpaces(out, offset);
                out.println(
                        "IF " + toStringCondition(c) + " THEN");
                childIf.prettyPrint(out, offset + indent);

                printSpaces(out, offset);
                out.println("ELSE");
                childElse.prettyPrint(out, offset + indent);

                printSpaces(out, offset);
                out.println("END IF");
                this.assembleIfElse(c, childIf, childElse);

                break;
            }
            case WHILE: {

                Statement child = this.newInstance();
                Condition c = this.disasembleWhile(child);
                printSpaces(out,offset);
                out.println(
                        "WHILE " + toStringCondition(c) + " DO");
                child.prettyPrint(out, offset + indent);

                printSpaces(out, offset);
                out.println("END WHILE");
                this.assembleWhile(c, child);

                break;
            }
            case CALL: {

                String call = this.disassembleCall();
                printSpaces(out,offset);
                out.println(call);
                this.assembleCall(call);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
        }
    }
}