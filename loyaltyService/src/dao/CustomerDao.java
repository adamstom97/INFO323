/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Coupon;
import domain.Customer;
import domain.Transaction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.NotFoundException;

/**
 * A data access object for the service. Contains a list of customers, and
 * methods to access and alter the lists of transactions and coupons for each of
 * those customers.
 *
 * @author adamstom97
 */
public class CustomerDao {

    private static Map<String, Customer> customers = new HashMap<>();

    // Instances of a specific customer's transaction and coupon collections.
    private Map<String, Transaction> transactions;
    private Map<Integer, Coupon> coupons;

    public CustomerDao() {
    }

    // Constructor that initialises the specific customer collections.
    public CustomerDao(String customerId) {
        if (customers.containsKey(customerId)) {
            transactions = customers.get(customerId).getTransactions();
            coupons = customers.get(customerId).getCoupons();
        } else {
            throw new NotFoundException("There is no customer with that ID.");
        }
    }

    /* 
    METHODS FOR CUSTOMERS
     */
    public static Map<String, Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(Map<String, Customer> customers) {
        CustomerDao.customers = customers;
    }

    public static void createCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    /* 
    METHODS FOR TRANSACTIONS AND COUPONS
     */
    public Collection<Transaction> getTransactions() {
        return transactions.values();
    }

    public Collection<Coupon> getCoupons() {
        return coupons.values();
    }

    public Transaction getTransactionById(String transactionId) {
        return transactions.get(transactionId);
    }

    public Coupon getCouponById(Integer couponId) {
        return coupons.get(couponId);
    }

    public Boolean transactionExists(String transactionId) {
        return transactions.containsKey(transactionId);
    }

    public Boolean couponExists(Integer couponId) {
        return coupons.containsKey(couponId);
    }

    public void createTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    public void createCoupon(Coupon coupon) {
        coupons.put(coupon.getId(), coupon);
    }

    public void deleteTransaction(Transaction transaction) {
        transactions.remove(transaction.getId());
    }

    public void deleteCoupon(Coupon coupon) {
        coupons.remove(coupon.getId());
    }

    public void updateCoupon(Integer couponId, Coupon updatedCoupon) {
        coupons.put(couponId, updatedCoupon);
    }
}
