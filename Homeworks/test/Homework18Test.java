import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.stack.Stack;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     *
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     *
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     * Tests Default Constructor.
     */
    @Test
    public final void testDefaultConstructor() {
        Stack<String> s = this.constructorTest();
        Stack<String> sExpected = this.constructorRef();

        assertEquals(sExpected, s);
    }

    /*
     * Push Tests --------------------------------------------------
     */

    /**
     * Tests Push.
     */
    @Test
    public final void testPushFromEmpty() {
        Stack<String> s = this.createFromArgsTest();
        Stack<String> sExpected = this.createFromArgsRef("Ricky");

        s.push("Ricky");

        assertEquals(sExpected, s);
    }

    /**
     * Tests Push-non empty.
     */
    @Test
    public final void testPushFromNonEmpty() {
        Stack<String> s = this.createFromArgsTest("Ricky");
        Stack<String> sExpected = this.createFromArgsRef("Daniel", "Ricky");

        s.push("Daniel");

        assertEquals(sExpected, s);
    }
    /*
     * Pop Tests --------------------------------------------------
     */

    /**
     * Tests Pop.
     */
    @Test
    public final void testPopToEmpty() {
        Stack<String> s = this.createFromArgsTest("Ricky");
        Stack<String> sExpected = this.createFromArgsRef();

        String ans = s.pop();

        assertEquals(sExpected, s);
        assertEquals("Ricky", ans);
    }

    /**
     * Tests Pop-non empty.
     */
    @Test
    public final void testPopToNonEmpty() {
        Stack<String> s = this.createFromArgsTest("Daniel", "Ricky");
        Stack<String> sExpected = this.createFromArgsRef("Ricky");

        String ans = s.pop();

        assertEquals(sExpected, s);
        assertEquals("Daniel", ans);
    }
    /*
     * Length Tests --------------------------------------------------
     */

    /**
     * Tests Length.
     */
    @Test
    public final void testLength() {
        Stack<String> s = this.createFromArgsTest("Ricky");

        int l = s.length();

        assertEquals(5, l);
    }

}