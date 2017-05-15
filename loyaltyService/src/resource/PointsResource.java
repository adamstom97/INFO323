package resource;

import dao.CustomerDao;
import domain.Coupon;
import domain.Transaction;
import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * A RESTful resource for calculating and representing a customer's points.
 *
 * @author adath325
 */
@Path("/customers/{customerId}/points")
public class PointsResource {

    private final CustomerDao dao;
    private Collection<Transaction> transactions;
    private Collection<Coupon> coupons;

    public PointsResource(@PathParam("customerId") String customerId) {
        dao = new CustomerDao(customerId);

        transactions = dao.getTransactions();
        coupons = dao.getCoupons();
    }

    @GET
    @Path("/total")
    public Integer getTotal() {
        Integer total = 0;
        for (Transaction t : transactions) {
            total += t.getPoints();
        }
        return total;
    }

    @GET
    @Path("/unused")
    public Integer getUnused() {
        Integer total = getTotal();
        for (Coupon coupon : coupons) {
            if (coupon.getUsed()) {
                total -= coupon.getPoints();
            }
        }
        return total;
    }
}
