import components.program.Program1;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework30 extends Program1 {

    /*
     * Secondary methods ------------------------------------------------------
     */

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Homework30() {
        super();
    }

    /**
     * Returns the location of the next primitive instruction to execute in
     * compiled program {@code cp} given what the bug sees {@code wbs} and
     * starting from location {@code pc}.
     *
     * @param cp
     *            the compiled program
     * @param wbs
     *            the {@code CellState} indicating what the bug sees
     * @param pc
     *            the program counter
     * @return the location of the next primitive instruction to execute
     * @requires <pre>
     * [cp is a valid compiled BL program]  and
     * 0 <= pc < cp.length  and
     * [pc is the location of an instruction byte code in cp, that is, pc
     *  cannot be the location of an address]
     * </pre>
     * @ensures <pre>
     * [return the address of the next primitive instruction that
     *  should be executed in program cp given what the bug sees wbs and
     *  starting execution at address pc in program cp]
     * </pre>
     */
    public static int nextPrimitiveInstructionAddress(int[] cp, CellState wbs,
            int pc) {
        int end = pc;

        while (end < cp.length && !isPrimitiveInstructionByteCode(cp[end])) {
            if (conditionalJumpCondition(wbs, cp[end])) {
                end = pc + 2;
            } else {
                end = pc + 1;
            }
        }
        return end;
    }
}