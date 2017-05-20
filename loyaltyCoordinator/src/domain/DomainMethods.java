package domain;


import domain.Transaction;


/**
 *
 * @author adath325
 */
public class DomainMethods {

	public Integer calculatePoints(Double price) {
		return price.intValue() / 10;
	}

	public Transaction createTransaction(String id, String shop,
			  Integer points) {
		return new Transaction(id, shop, points);
	}
}
