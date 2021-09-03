import components.sequence.Sequence;
import components.sequence.Sequence1L;

/**
 * Customized JUnit test fixture for {@code Sequence3}.
 */
public class Sequence3Test extends SequenceTest {

    @Override
    protected final Sequence<String> constructorTest() {
        return new Sequence3<String>();
    }

    @Override
    protected final Sequence<String> constructorRef() {
        return new Sequence1L<String>();
    }

}
