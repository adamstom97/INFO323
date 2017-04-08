package remote;

import domain.Sale;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author adath325
 */
public class SalesAggregationImpl extends UnicastRemoteObject implements
		  ISalesAggregation {
	
	private Collection<Sale> sales = new ArrayList<>();

	@Override
	public void newSale(Sale sale) throws RemoteException {
		sales.add(sale);
	}
	
}
