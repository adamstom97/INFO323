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
        
        Collection<Sale> testSales = server.getSales();
        assertEquals("Is the collection of sales the correct length?", 1, 
                testSales.size());
        
        Sale testSale = testSales.iterator().next();
        assertEquals("Does the stored sale have the correct date?", "13/04/17",
                testSale.getDate());
        
        Customer testCustomer = testSale.getCustomer();
        assertEquals("Does the stored customer have the correct gender?", 
                (Character) 'M', testCustomer.getGender());
        assertEquals("Does the stored customer have the correct date of birth?",
                "27/04/97", testCustomer.getDateOfBirth());
        
        Collection<SaleItem> testItems = testSale.getItems();
        assertEquals("Is the collection of items the correct length?", 1, 
                testItems.size());
        
        SaleItem testItem = testItems.iterator().next();
        assertEquals("Does the stored item have the correct product ID?", 
                "FW1234", testItem.getProductId());
        assertEquals("Does the stored item have the correct quantity?", 
                (Double) 10.0, testItem.getQuantity());
        assertEquals("Does the stored item have the correct price?", 
                (Double) 3.5, testItem.getPrice());
    }
}
