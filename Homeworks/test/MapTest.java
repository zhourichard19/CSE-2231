import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Richard Zhou
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    @Test
    // Test add
    public void addTest() {

        // Set up variables

        Map<String, String> m = this.createFromArgsTest("Ricky", "Daniel");

        Map<String, String> mExpected = this.createFromArgsTest("Ricky",
                "Daniel", "Luke", "Ian");

        // Method Call

        m.add("Luke", "Ian");

        // Test

        assertEquals(m, mExpected);

    }

    @Test
    // Test remove
    public void removeTest() {

        // Set up variables

        Map<String, String> m = this.createFromArgsTest("Ricky", "Daniel",
                "Luke", "Ian");

        Map<String, String> mExpected = this.createFromArgsTest("Ricky",
                "Daniel",

                "Luke");

        // Method Call

        m.remove("Ian");

        // Test

        assertEquals(m, mExpected);

    }

    @Test
    // Test removeAny
    public void removeAnyTest() {

        // Set up variables

        Map<String, String> m = this.createFromArgsTest("Ricky", "Daniel",
                "Luke", "Ian");

        Map<String, String> mExpected = this.createFromArgsTest("Ricky",
                "Daniel", "Luke",

                "Ian");

        // Method Call

        Map<String, String> x = (Map<String, String>) m.removeAny();

        // Test

        assertEquals(true, x.sharesKeyWith(mExpected));

        mExpected.remove(x.toString());

        assertEquals(m, mExpected);

    }

    @Test
    // Test value
    public void valueTest() {

        // Set up variables

        Map<String, String> m = this.createFromArgsTest("Ricky", "Daniel",
                "Luke", "Ian");

        String vExpected = "Ian";

        // Method Call

        String v = m.value("Luke");

        // Test

        assertEquals(v, vExpected);

    }

    @Test
    // Test key
    public void hasKeyTest() {

        // Set up variables

        Map<String, String> m = this.createFromArgsTest("Ricky", "Daniel",
                "Luke", "Ian");

        boolean bExpected = true;

        // Method Call

        boolean b = m.hasKey("Ricky");

        // Test

        assertEquals(b, bExpected);

    }

    @Test
    // Test size
    public void sizeTest() {

        // Set up variables

        Map<String, String> m = this.createFromArgsTest("Ricky", "Daniel",
                "Luke", "Ian");

        int sizeExpected = 4;

        // Method Call

        int size = m.size();

        // Test

        assertEquals(size, sizeExpected);

    }

}