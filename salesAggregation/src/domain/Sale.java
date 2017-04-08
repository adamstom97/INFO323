package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A domain class for representing a sale.
 * 
 * @author adath325
 */
public class Sale implements Serializable {
	private String date;
	private Customer customer;
	
	private Collection<SaleItem> items = new ArrayList<>();

	public Sale() {
	}

	public Sale(String date, Customer customer) {
		this.date = date;
		this.customer = customer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Collection<SaleItem> getItems() {
		return items;
	}

	public void setItems(Collection<SaleItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Sale{" + "date=" + date + ", customer=" + customer + ", items=" + 
				  items + '}';
	}
}
