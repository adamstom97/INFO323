/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A domain class for representing a customer.
 *
 * @author adamstom97
 */
@XmlRootElement
public class Customer implements Serializable {

	private String id;
	private Map<String, Transaction> transactions = new HashMap<>();
	private Map<Long, Coupon> coupons = new HashMap<>();

	public Customer() {
	}

	public Customer(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Map<String, Transaction> transactions) {
		this.transactions = transactions;
	}

	public Map<Long, Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Map<Long, Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer{" + "id=" + id + ", transactions=" + transactions
				  + ", coupons=" + coupons + '}';
	}
}
