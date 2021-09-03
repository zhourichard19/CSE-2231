import components.sequence.Sequence;
import components.tree.Tree;

/**
 * Simple HelloWorld program (clear of Checkstyle and FindBugs warnings).
 *
 * @author P. Bucci
 */
public final class Mathematicaltee {

    /**
     * Default constructor--private to prevent instantiation.
     */
    private Mathematicaltee() {
        // no code needed here
    }

    /**
     * Returns the size of the given {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose size to return
     * @return the size of the given {@code Tree}
     * @ensures size = |t|
     */
    public static <T> int size(Tree<T> t) {

        int size = 0;
        Sequence<Tree<T>> child = t.newSequenceOfTree();

        if (t.height() > 0) {
            T root = t.disassemble(child);
            size++;
            for (int i = 0; i < child.length(); i++) {
                Tree tempTree = child.remove(i);
                size += size(tempTree);
            }
            t.assemble(root, child);
        }
        return size;
    }
    
    /**
     * Returns the size of the given {@code Tree<T>}.
     *
     * @param <T>
     *            the type of the {@code Tree} node labels
     * @param t
     *            the {@code Tree} whose size to return
     * @return the size of the given {@code Tree}
     * @ensures size = |t|
     */
    public static <T> int nonRecursiveSize(Tree<T> t) {
        int size = 0;

        for (T x : t) {
            size++;
        }

        return size;
    }

    /**
     * Returns the largest integer in the given {@code Tree<Integer>}.
     *
     * @param t
     *            the {@code Tree<Integer>} whose largest integer to return
     * @return the largest integer in the given {@code Tree<Integer>}
     * @requires |t| > 0
     * @ensures <pre>
     * max is in labels(t)  and
     * for all i: integer where (i is in labels(t)) (i <= max)
     * </pre>
     */
    public static int max(Tree<Integer> t) {
        int max = 0;
        Sequence<Tree<T>> child = t.newSequenceOfTree();
        if (t.height() != 0) {
            int root = t.disassemble(child);
            for (int i = 0; i < child.length(); i++) {
                Tree<T> tempTree = child.remove(1);
                int tempMax = max(tempTree);
                if (tempMax > max) {
                    max = tempMax;
                }
            }
            t.assemble(root, child);
        }
        return max;
    }

}
