/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Coupon;
import java.util.HashMap;
import java.util.Map;

/**
 * A data access object for storing coupons.
 * 
 * @author adamstom97
 */
public class CouponDao {
    private Map<Integer, Coupon> coupons = new HashMap<>();

    public CouponDao() {
    }
    
    public void newCoupon(Coupon coupon) {
        Integer id = coupon.getId();
        coupons.put(id, coupon);
    }

    public Map<Integer, Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Map<Integer, Coupon> coupons) {
        this.coupons = coupons;
    }
}
