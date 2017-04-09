package remote;

import domain.Sale;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An implementation of the ISalesAggregation interface, allowing a remote
 * client to add a new sale to a collection of sales.
 * 
 * @author adath325
 */
public class SalesAggregationImpl extends UnicastRemoteObject implements
        ISalesAggregation {

    private Collection<Sale> sales = new ArrayList<>();

    public SalesAggregationImpl() throws RemoteException {
    }
    
    @Override
    public void newSale(Sale sale) throws RemoteException {
        sales.add(sale);
    }
}
