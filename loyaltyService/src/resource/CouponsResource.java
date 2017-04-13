package resource;

import dao.CustomerDao;
import domain.Coupon;
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
@Path("/customers/{customerId}/coupons")
public class CouponsResource {

    private final CustomerDao dao;

    public CouponsResource(@PathParam("customerId") String customerId) {
        dao = new CustomerDao(customerId);
    }

    @GET
    public Collection<Coupon> getCoupons() {
        return dao.getCoupons();
    }

    @POST
    public Response createCoupon(Coupon coupon, @Context UriInfo uri) {
        dao.createCoupon(coupon);
        URI newURI = uri.getAbsolutePathBuilder()
                .path(coupon.getId().toString()).build();
        return Response.created(newURI).build();
    }
}
