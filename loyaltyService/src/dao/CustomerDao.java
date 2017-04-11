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

/**
 * A data access object for the service.
 * 
 * @author adamstom97
 */
public class CustomerDao {
    private static Map<String, Customer> customers = new HashMap<>();

    public CustomerDao() {
    }

    public static Map<String, Customer> getCustomers() {
        return customers;
    }

    public static void setCustomers(Map<String, Customer> customers) {
        CustomerDao.customers = customers;
    }
    
    public Collection<Transaction> getTransactions(String customerId) {
        Map<String, Transaction> transactions = customers.get(customerId).getTransactions();
        return transactions.values();
    }
    
    public Collection<Coupon> getCoupons(String customerId) {
        Map<Integer, Coupon> coupons = customers.get(customerId).getCoupons();
        return coupons.values();
    }
    
    public Transaction getTransactionById(String customerId, String transactionId) {
        Map<String, Transaction> transactions = customers.get(customerId).getTransactions();
        Transaction transaction = transactions.get(transactionId);
        return transaction;        
    }
    
    public Coupon getCouponById(String customerId, Integer couponId) {
        Map<Integer, Coupon> coupons = customers.get(customerId).getCoupons();
        Coupon coupon = coupons.get(couponId);
        return coupon;        
    }
    
    public Boolean transactionExists(String customerId, String transactionId) {
        Map<String, Transaction> transactions = customers.get(customerId).getTransactions();
        return transactions.containsKey(transactionId);
    }
    
    public Boolean couponExists(String customerId, Integer couponId) {
        Map<Integer, Coupon> coupons = customers.get(customerId).getCoupons();
        return coupons.containsKey(couponId);
    }
    
    public void createTransaction(String customerId, Transaction transaction) {
        Map<String, Transaction> transactions = customers.get(customerId).getTransactions();
        transactions.put(transaction.getId(), transaction);
    }
    
    public void createCoupon(String customerId, Coupon coupon) {
        Map<Integer, Coupon> coupons = customers.get(customerId).getCoupons();
        coupons.put(coupon.getId(), coupon);
    }
    
    public void deleteTransaction(String customerId, Transaction transaction) {
        Map<String, Transaction> transactions = customers.get(customerId).getTransactions();
        transactions.remove(transaction.getId());
    }
    
    public void deleteCoupon(String customerId, Coupon coupon) {
        Map<Integer, Coupon> coupons = customers.get(customerId).getCoupons();
        coupons.remove(coupon.getId());
    }
}
