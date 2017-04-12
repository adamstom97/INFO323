package resource;

import dao.CustomerDao;
import domain.Transaction;
import java.net.URI;
import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * A RESTful resource for representing a collection of transactions.
 *
 * @author adath325
 */
@Path("/customers/{customerId}/transactions")
public class TransactionsResource {

	private final CustomerDao dao;

	public TransactionsResource(@PathParam("customerId") String customerId) {
		dao = new CustomerDao(customerId);
	}

	@GET
	public Collection<Transaction> getTransactions() {
		return dao.getTransactions();
	}

	@POST
	public Response createTransaction(Transaction transaction, @Context UriInfo uri) {
		dao.createTransaction(transaction);
		URI newURI = uri.getAbsolutePathBuilder().path(transaction.getId()).build();
		return Response.created(newURI).build();
	}
}
