import components.standard.Standard;

/**
 * First-in-first-out (FIFO) waiting line component with primary methods.
 *
 * @param <T>
 *            type of {@code WaitingLineKernel} entries
 * @mathmodel type WaitingLineKernel is modeled by string of T
 * @initially <pre>
 * ():
 *  ensures
 *   this = <>
 * </pre>
 * @iterator ~this.seen * ~this.unseen = this
 */
public interface WaitingLineKernel<T>
        extends Standard<WaitingLine<T>>, Iterable<T> {

    /**
     * Adds a unit or T person to the end of the line as they show up in order
     *
     * @param person
     *            the thing that will added to the line
     * @updates this
     * @requires <pre>
     *  this cannot contain person
     * &#64;ensures
     *      this = #this * <person>}
     * </pre>
     */
    void add(T person);

    /**
     * Removes the first person in the line
     *
     * @return the entry that is removed
     * @updates this
     * @requires <pre>
     * this is not empty
     * </pre>
     * @ensures <pre>
     * this has the first entry removed
     * </pre>
     */
    T removeFrontEntry();

    /**
     * Returns the first person in line
     *
     * @return the first person in the line
     * @updates this
     * @requires <pre>
     * this is not empty
     * </pre>
     * @ensures <pre>
     * there is something in line and they are the first
     * </pre>
     */
    T firstInLine();

    /**
     * Returns the last person in line
     *
     * @return the last person in the line
     * @updates this
     * @requires <pre>
     * this is not empty
     * </pre>
     * @ensures <pre>
     * there is something in line and they are the last
     * </pre>
     */
    T lastInLine();

    /**
     * Returns the length of the line
     *
     * @return the amount of people in the line
     * @updates this
     * @requires <pre>
     * this is not empty
     * </pre>
     * @ensures <pre>
     * the length is |line|
     * </pre>
     */
    T length();

    /**
     * removes the entry in the line given the position and removes that entry
     *
     * @param pos
     *            the position of the person that will be removed
     * @return the removed person
     * @updates this
     * @requires <pre>
     * this to be empty
     * </pre>
     * @ensures <pre>
     *this will be updated and the position that will be removed is the one that
     *was given
     * </pre>
     */

    T removeEntry(int pos);

    /**
     * finds the position of an entry inside of a line
     *
     * @param entry
     *            the entry that is supposed to be found
     * @return the position of the given entry
     * @updates this
     * @requires <pre>
     * this to be empty
     * </pre>
     * @ensures <pre>
     * this will be returning the position of the given entry
     *was given
     * </pre>
     */

    T findEntry(T entry);
}
