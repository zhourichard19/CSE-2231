import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * JUnit test fixture for {@code CountPrimitiveCalls}'s static method
 * countOfPrimitiveCalls.
 *
 * @author Put your name here
 *
 */
public final class CountPrimitiveCallsTest {

    /**
     * Creates and returns a {@code Statement} constructed from a given input
     * file.
     *
     * @param fileName
     *            the name of the file containing the statement
     * @param block
     *            flag to indicate whether to read an entire BLOCK (sequence of
     *            statements) or a single statement
     * @return the constructed statement
     * @requires <pre>
     * [fileName is the name of a file containing zero, one, or more
     *  valid BL statements]
     * </pre>
     * @ensures createFromArgs = [statement(s) from file fileName]
     */
    private Statement createFromArgs(String fileName, boolean block) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(in);
        in.close();
        Statement s = new Statement1();
        if (block) {
            s.parseBlock(tokens);
        } else {
            s.parse(tokens);
        }
        return s;
    }

    @Test
    public void test1true() {
        Statement s1 = this.createFromArgs("data/test1.bl", true);
        Statement s2 = this.createFromArgs("data/test1.bl", true);
        int count = CountPrimitiveCalls.countOfPrimitiveCalls(s1);
        assertEquals(25, count);
        assertEquals(s2, s1);
    }

    // TODO: add other (smaller) test cases as needed

}
