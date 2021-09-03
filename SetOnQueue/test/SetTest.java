import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Daniel Wu
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    @Test
    // Test add
    public void testAdd() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        Set<String> sExpected = this.createFromArgsRef("This", "is", "a",
                "test");
        // Method call
        s.add("test");

        assertEquals(s, sExpected);
    }

    @Test
    // Test remove
    public void testRemove() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        Set<String> sExpected = this.createFromArgsRef("This", "is");
        // Method call
        s.remove("a");

        assertEquals(s, sExpected);
    }

    @Test
    // Test contains
    public void testContains() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        String string = "is";

        // Method call
        boolean b = s.contains(string);

        assertEquals(b, true);
    }

    @Test
    // Test size
    public void testSize() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        int xExpected = 3;
        // Method call
        int x = s.size();

        assertEquals(x, xExpected);
    }

    @Test
    // Test removeAny
    public void testRemoveAny() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        Set<String> sExpected = this.createFromArgsRef("This", "is", "a");
        // Method call
        String x = s.removeAny();

        assertEquals(sExpected.contains(x), true);
        sExpected.remove(x);
        assertEquals(sExpected, s);
    }

}
