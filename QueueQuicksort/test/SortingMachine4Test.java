import java.util.Comparator;

import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Customized JUnit test fixture for {@code SortingMachine4}.
 */
public final class SortingMachine4Test extends SortingMachineTest {

    @Override
    protected SortingMachine<String> constructorTest(Comparator<String> order) {
        return new SortingMachine4<String>(order);
    }

    @Override
    protected SortingMachine<String> constructorRef(Comparator<String> order) {
        return new SortingMachine1L<String>(order);
    }

}
