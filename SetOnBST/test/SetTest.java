import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
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
    // Test add 0
    public void testAdd0() {
        // Set up variables
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("test");
        // Method call
        s.add("test");

        assertEquals(s, sExpected);
    }

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

    // Test add
    public void testAdd2() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is");
        Set<String> sExpected = this.createFromArgsRef("This", "is", "a",
                "test");
        // Method call
        s.add("a");
        s.add("test");

        assertEquals(s, sExpected);
    }

    // Test add
    public void testAddMore() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a", "harry",
                "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian");
        Set<String> sExpected = this.createFromArgsRef("This", "is", "a",
                "harry", "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian",
                "Maggie");
        // Method call
        s.add("Maggie");

        assertEquals(s, sExpected);
    }

    @Test
    // Test remove
    public void testRemove1() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This");
        Set<String> sExpected = this.createFromArgsRef();
        // Method call
        String x = s.remove("This");

        assertEquals(x, "This");
        assertEquals(s, sExpected);
    }

    @Test
    // Test remove
    public void testRemove() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        Set<String> sExpected = this.createFromArgsRef("This", "is");
        // Method call
        String x = s.remove("a");

        assertEquals(x, "a");
        assertEquals(s, sExpected);
    }

    @Test
    // Test remove
    public void testRemoveTwice() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        Set<String> sExpected = this.createFromArgsRef("This");
        // Method call
        String x = s.remove("a");
        String y = s.remove("is");

        assertEquals(x, "a");
        assertEquals(y, "is");
        assertEquals(s, sExpected);
    }

    @Test
    // Test remove
    public void testRemoveALot() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a", "harry",
                "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian", "Maggie");
        Set<String> sExpected = this.createFromArgsRef("This", "is", "a",
                "harry", "Ricky", "Luke");
        // Method call
        String v = s.remove("Maggie");
        String w = s.remove("ian");
        String x = s.remove("Dr.Heyme");
        String y = s.remove("Omer");
        String z = s.remove("Daniel");

        assertEquals(v, "Maggie");
        assertEquals(w, "ian");
        assertEquals(x, "Dr.Heyme");
        assertEquals(y, "Omer");
        assertEquals(z, "Daniel");
        assertEquals(s, sExpected);
    }

    @Test
    // Test remove
    public void testRemoveAll() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        Set<String> sExpected = this.createFromArgsRef();
        // Method call
        String x = s.remove("a");
        String y = s.remove("is");
        String z = s.remove("This");

        assertEquals(x, "a");
        assertEquals(y, "is");
        assertEquals(z, "This");
        assertEquals(s, sExpected);
    }

    @Test
    // Test contains
    public void testContains0() {
        // Set up variables
        Set<String> s = this.createFromArgsTest();
        String string = "is";

        // Method call
        boolean b = s.contains(string);

        assertEquals(b, false);
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
    // Test contains
    public void testContains1False() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        String string = "isa";

        // Method call
        boolean b = s.contains(string);

        assertEquals(b, false);
    }

    @Test
    // Test contains
    public void testContainsBig() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a", "harry",
                "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian", "Maggie");
        String string = "ian";

        // Method call
        boolean b = s.contains(string);

        assertEquals(b, true);
    }

    @Test
    // Test contains
    public void testContainsBigFalse() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a", "harry",
                "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian", "Maggie");
        String string = "iasdfn";

        // Method call
        boolean b = s.contains(string);

        assertEquals(b, false);
    }

    @Test
    // Test size
    public void testSize0() {
        // Set up variables
        Set<String> s = this.createFromArgsTest();
        int xExpected = 0;
        // Method call
        int x = s.size();

        assertEquals(x, xExpected);
    }

    @Test
    // Test size
    public void testSize1() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("Hi");
        int xExpected = 1;
        // Method call
        int x = s.size();

        assertEquals(x, xExpected);
    }

    @Test
    // Test size
    public void testSize3() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a");
        int xExpected = 3;
        // Method call
        int x = s.size();

        assertEquals(x, xExpected);
    }

    @Test
    // Test size
    public void testSizeBig() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a", "harry",
                "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian", "Maggie");
        int xExpected = 11;
        // Method call
        int x = s.size();

        assertEquals(x, xExpected);
    }

    @Test
    // Test removeAny
    public void testRemoveAny0() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This");
        Set<String> sExpected = this.createFromArgsRef("This");
        // Method call
        String x = s.removeAny();

        assertEquals(sExpected.contains(x), true);
        sExpected.remove(x);
        assertEquals(sExpected, s);
    }

    @Test
    // Test removeAny
    public void testRemoveAny2() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "a");
        Set<String> sExpected = this.createFromArgsRef("This", "a");
        // Method call
        String x = s.removeAny();

        assertEquals(sExpected.contains(x), true);
        sExpected.remove(x);
        assertEquals(sExpected, s);
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

    @Test
    // Test removeAny
    public void testRemoveAnyBig() {
        // Set up variables
        Set<String> s = this.createFromArgsTest("This", "is", "a", "harry",
                "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian", "Maggie");
        Set<String> sExpected = this.createFromArgsRef("This", "is", "a",
                "harry", "Ricky", "Luke", "Daniel", "Omer", "Dr.Heyme", "ian",
                "Maggie");
        // Method call
        String x = s.removeAny();

        assertEquals(sExpected.contains(x), true);
        sExpected.remove(x);
        assertEquals(sExpected, s);
    }

}
