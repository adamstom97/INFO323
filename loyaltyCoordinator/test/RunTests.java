
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A Test Suite, to run all the loylatyCoordinator tests at once.
 *
 * @author adath325
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
	CustomerCreatesCouponTest.class,
	CustomerMakesPurchaseTest.class,
	CustomerUsesCouponTest.class,
	CustomerViewsPointsTest.class
})

public class RunTests {
}
