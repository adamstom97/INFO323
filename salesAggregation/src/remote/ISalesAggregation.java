package remote;

import domain.Sale;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for the client side of our RMI service.
 * 
 * @author adath325
 */
public interface ISalesAggregation extends Remote {
	
	/**
	 * Saves a new instance of Sale in a collection.
	 * 
	 * @param sale the new instance of Sale
	 * @throws RemoteException
	 */
	void newSale(Sale sale) throws RemoteException;
}
