@Override

public final boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }
    if (obj == null) {
        return false;
    }
    if (!(obj instanceof Queue<?>)) {
        return false;
    }
    Queue<?> q = (Queue<?>) obj;
    if (this.lengthOfLine() != q.length()) {
        return false;
    }
    Iterator<T> it1 = this.iterator();
    Iterator<?> it2 = q.iterator();
    while (it1.hasNext()) {
        T x1 = it1.next();
        Object x2 = it2.next();
        if (!x1.equals(x2)) {
            return false;
        }
    }
    return true;
}


@Override
public int hashCode() {
    final int numgerOfObjects = 2;
    final int x = 37;
    final int y = 17;
    int result = 0;

    int n = 0;
    Iterator<T> iterator = this.iterator();
    while (n < samples && iterator.hasNext()) {
        n++;
        T t = it.next();
        result = x * result + y * t.hashCode();
    }
    return result;
}

// CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
@Override
public String toString() {
    StringBuilder result = new StringBuilder("<");
    Iterator<T> it = this.iterator();
    while (it.hasNext()) {
        String next = it.next()
        result.append(next);
        if (it.hasNext()) {
            result.append(",");
        }
    }
    result.append(">");
    return result.toString();
}

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
@Override
public int findEntry(T entry) {

    int pos = 0;
    for (int i = 0; i < this.lengthOfLine(); i++) {
        if (this.frontLine().equals(entry)) {
            pos = i;
        }
        this.addLine(this.removeFrontLine());
    }
    return pos;

}
