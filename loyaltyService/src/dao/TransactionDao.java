/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Transaction;
import java.util.HashMap;
import java.util.Map;

/**
 * A data access object for storing transactions.
 * 
 * @author adamstom97
 */
public class TransactionDao {
    private Map<String, Transaction> transactions = new HashMap<>();

    public TransactionDao() {
    }
    
    public void newTransaction (Transaction transaction) {
        String id = transaction.getId();
        transactions.put(id, transaction);
    }

    public Map<String, Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<String, Transaction> transactions) {
        this.transactions = transactions;
    }

}
