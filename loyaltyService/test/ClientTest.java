/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.Coupon;
import domain.Transaction;
import java.util.Collection;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adamstom97
 */
public class ClientTest {

    private static WebTarget transactions;
    private static WebTarget coupons;
    private static WebTarget points;

    private Transaction transaction;
    private Coupon coupon1;
    private Coupon coupon2;

    public ClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        transactions = client.target(
                "http://localhost:8081/customers/CustID/transactions/");
        coupons = client.target(
                "http://localhost:8081/customers/CustID/coupons/");
        points = client.target(
                "http://localhost:8081/customers/CustID/points/");
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        transaction = new Transaction("1234", "Dunedin", 5);
        coupon1 = new Coupon(1, 2, true);
        coupon2 = new Coupon(2, 3, false);

        transactions.request().post(Entity.entity(transaction, 
                "application/json"));
        coupons.request().post(Entity.entity(coupon1, "application/json"));
        coupons.request().post(Entity.entity(coupon2, "application/json"));
    }

    @After
    public void tearDown() {
        transactions.path("1234").request().delete();
        coupons.path("1").request().delete();
        coupons.path("2").request().delete();
    }

    @Test
    public void testPostGetTransaction() {
        Transaction transactionGet = transactions.path(transaction.getId())
                .request("text/xml").get(Transaction.class);
        Assert.assertEquals("Test POST transaction with XML", transactionGet, 
                transaction);

        transactionGet = transactions.path(transaction.getId())
                .request("application/json").get(Transaction.class);
        Assert.assertEquals("Test POST transaction with JSON", transactionGet, 
                transaction);
    }

    @Test
    public void testPostGetCoupon() {
        Coupon couponGet = coupons.path(String.valueOf(coupon1.getId()))
                .request("text/xml").get(Coupon.class);
        Assert.assertEquals("Test POST coupon with XML", couponGet, coupon1);

        couponGet = coupons.path(String.valueOf(coupon1.getId()))
                .request("application/json").get(Coupon.class);
        Assert.assertEquals("Test POST coupon with JSON", couponGet, coupon1);
    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void testDeleteTransaction() {
        transactions.path(transaction.getId()).request().delete();

        Transaction transactionGet = transactions.path(transaction.getId())
                .request("text/xml").get(Transaction.class);
        transactionGet = transactions.path(transaction.getId())
                .request("application/json").get(Transaction.class);
    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void testDeleteCoupon() {
        coupons.path(String.valueOf(coupon1.getId())).request().delete();

        Coupon couponGet = coupons.path(String.valueOf(coupon1.getId()))
                .request("text/xml").get(Coupon.class);
        couponGet = coupons.path(String.valueOf(coupon1.getId()))
                .request("application/json").get(Coupon.class);
    }

    @Test
    public void testPutCoupon() {
        Coupon couponPut = new Coupon(2, 3, true);
        coupons.path(String.valueOf(coupon2.getId()))
                .request().put(Entity.entity(couponPut, "text/xml"));
        Coupon couponGet = coupons.path(String.valueOf(coupon2.getId()))
                .request("text/xml").get(Coupon.class);
        Assert.assertEquals("Test PUT coupon with XML", couponGet.getUsed(), 
                couponPut.getUsed());

        couponPut = new Coupon(2, 3, false);
        coupons.path(String.valueOf(coupon2.getId()))
                .request().put(Entity.entity(couponPut, "application/json"));
        couponGet = coupons.path(String.valueOf(coupon2.getId()))
                .request("application/json").get(Coupon.class);
        Assert.assertEquals("Test PUT coupon with JSON", couponGet.getUsed(), 
                couponPut.getUsed());
    }

    @Test
    public void testGetTransactions() {
        Collection<Transaction> transactionsGet = transactions
                .request("text/xml")
                .get(new GenericType<Collection<Transaction>>() {
        });
        Assert.assertTrue("Test GET transactions with XML", 
                transactionsGet.contains(transaction));

        transactionsGet = transactions.request("application/json")
                .get(new GenericType<Collection<Transaction>>() {
        });
        Assert.assertTrue("Test GET transactions with JSON", 
                transactionsGet.contains(transaction));
    }

    @Test
    public void testGetCoupons() {
        Collection<Coupon> couponsGet = coupons.request("text/xml")
                .get(new GenericType<Collection<Coupon>>() {
        });
        Assert.assertTrue("Test GET coupons with XML", 
                couponsGet.contains(coupon1));

        couponsGet = coupons.request("application/json")
                .get(new GenericType<Collection<Coupon>>() {
        });
        Assert.assertTrue("Test GET coupons with JSON", 
                couponsGet.contains(coupon1));
    }

    @Test
    public void testTotalPoints() {
        Integer total = points.path("total")
                .request("text/plain").get(Integer.class);
        Assert.assertEquals("Test GET total points", total, 
                (Integer) (coupon1.getPoints() + coupon2.getPoints()));
    }

    @Test
    public void testUnusedPoints() {
        Integer total = points.path("unused")
                .request("text/plain").get(Integer.class);
        Assert.assertEquals("Test GET total unused points", total, 
                coupon2.getPoints());
    }
}
