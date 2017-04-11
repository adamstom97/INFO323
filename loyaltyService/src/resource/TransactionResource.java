/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import dao.CustomerDao;
import domain.Transaction;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * A RESTful resource for representing transactions.
 * 
 * @author adamstom97
 */

@Path("/customers/{customerId}/transactions/{transactionId}")
public class TransactionResource {
    private final CustomerDao dao = new CustomerDao();
    private String customerId;
    private String transactionId;
    private final Transaction resource;
    
    public TransactionResource(@PathParam("customerId") String customerId,
            @PathParam("transactionId") String transactionId) {
        if (dao.transactionExists(customerId, transactionId)) {
            this.transactionId = transactionId;
        } else {
            throw new NotFoundException("There is no transaction with that ID.");
        }
        this.resource = dao.getTransactionById(customerId, transactionId);
    }
    
    @GET
    public Transaction getTransaction() {
        return resource;
    }
    
    @DELETE
    public void deleteProduct() {
        dao.deleteTransaction(customerId, resource);
    }
}
