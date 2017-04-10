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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
    private static Registry registry;
    private static ISalesAgg server;
    private Sale sale;
    
    public ClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry("localhost");
        server = (ISalesAgg) registry.lookup("sales");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {     
        SaleItem item = new SaleItem("FW1234", 10.0, 3.5);
        Collection<SaleItem> items = new ArrayList<>();
        items.add(item);
        
        Customer customer = new Customer('M', "27/04/97");
        sale = new Sale("13/04/17", customer);
        sale.setItems(items);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void newSaleTest() throws RemoteException {
        server.newSale(sale);
    }
}
