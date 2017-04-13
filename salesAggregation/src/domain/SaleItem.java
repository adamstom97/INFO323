package domain;

import java.io.Serializable;

/**
 * A domain class for representing an individual sale item.
 *
 * @author adath325
 */
public class SaleItem implements Serializable {

    private String productId;
    private Double quantity;
    private Double price;

    public SaleItem() {
    }

    public SaleItem(String productId, Double quantity, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SaleItem{" + "productId=" + productId + ", quantity=" + quantity
                + ", price=" + price + '}';
    }
}
