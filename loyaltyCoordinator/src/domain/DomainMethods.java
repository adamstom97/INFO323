package domain;

/**
 * A class for the methods that the route builders call on domain objects.
 *
 * @author adath325
 */
public class DomainMethods {

	public Integer calculatePoints(Double price) {
		if (price > 0) {
			return price.intValue() / 10;
		}
		return 0;
	}

	public Transaction createTransaction(String id, String shop,
			  Integer points) {
		return new Transaction(id, shop, points);
	}
}
