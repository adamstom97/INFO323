/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.Customer;
import domain.Sale;
import domain.SaleItem;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Collection;
import remote.ISalesAgg;

/**
 *
 * @author adamstom97
 */
public class ClientTest {
    private static ISalesAgg server;
    
    public ClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost");
        server = (ISalesAgg) registry.lookup("sales");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void newSaleTest() throws RemoteException {
        SaleItem item = new SaleItem("FW1234", 10.0, 3.5);
        Collection<SaleItem> items = new ArrayList<>();
        items.add(item);
        
        Customer customer = new Customer('M', "27/04/97");
        Sale sale = new Sale("13/04/17", customer);
        sale.setItems(items);
        
        server.newSale(sale);
    }
}
