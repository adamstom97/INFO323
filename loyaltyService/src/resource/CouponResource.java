/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resource;

import dao.CustomerDao;
import domain.Coupon;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * A RESTful resource for representing a coupon.
 *
 * @author adamstom97
 */
@Path("/customers/{customerId}/coupons/{couponId}")
public class CouponResource {

    private final CustomerDao dao;
    private final Coupon resource;
    private Integer couponId;

    public CouponResource(@PathParam("customerId") String customerId,
            @PathParam("couponId") Integer couponId) {
        dao = new CustomerDao(customerId);

        if (dao.couponExists(couponId)) {
            this.resource = dao.getCouponById(couponId);
            this.couponId = couponId;
        } else {
            throw new NotFoundException("There is no coupon with that ID.");
        }
    }

    @GET
    public Coupon getCoupon() {
        return resource;
    }

    @DELETE
    public void deleteCoupon() {
        dao.deleteCoupon(resource);
    }

    @PUT
    public Response updateCoupon(Coupon updatedCoupon) {
        if (couponId.equals(updatedCoupon.getId())) {
            dao.updateCoupon(couponId, updatedCoupon);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.CONFLICT)
                    .entity("You cannot change the ID of a coupon.").build();
        }
    }
}
