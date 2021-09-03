import components.map.Map;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Richard Zhou
 */
public final class Homework25 {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Homework25() {
        // no code needed here
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires [the salaries in map are positive] and raisePercent > 0
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    private static void giveRaise(components.map.Map<String, Integer> map,
            char initial, int raisePercent) {

        Map<String, Integer> raisedMap = map.newInstance();
        for (int i = 0; i < map.size(); i++) {
            components.map.Map.Pair<String, Integer> temp = map.removeAny();
            if (temp.key().charAt(0) == initial) {
                int raise = temp.value() * (raisePercent);
                raisedMap.add(temp.key(), raise);
            } else {
                raisedMap.add(temp.key(), temp.value());
            }
        }
        map.transferFrom(raisedMap);
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires <pre>
     * [the salaries in map are positive]  and  raisePercent > 0  and
     * [the dynamic types of map and of all objects reachable from map
     *  (including any objects returned by operations (such as entrySet() and,
     *  from there, iterator()), and so on, recursively) support all
     *  optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    private static void giveRaise(java.util.Map<String, Integer> map,
            char initial, int raisePercent) {
        for (java.util.Map.Entry<String, Integer> x : map.entrySet()) {
            if (x.getKey().charAt(0) == initial) {
                x.setValue(x.getValue() * (initial));
            }
        }
    }
}
