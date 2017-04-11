package server;

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
public class SalesAggImpl extends UnicastRemoteObject implements
        ISalesAgg {

    private Collection<Sale> sales = new ArrayList<>();

    public SalesAggImpl() throws RemoteException {
    }
    
    @Override
    public void newSale(Sale sale) throws RemoteException {
        sales.add(sale);
    }

    public Collection<Sale> getSales() {
        return sales;
    }

    public void setSales(Collection<Sale> sales) {
        this.sales = sales;
    }
}
