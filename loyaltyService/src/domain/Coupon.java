/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 * A domain class for representing a coupon.
 * 
 * @author adamstom97
 */
public class Coupon implements Serializable {
    private Integer id;
    private Integer points;
    private Boolean used;

    public Coupon() {
    }

    public Coupon(Integer id, Integer points, Boolean used) {
        this.id = id;
        this.points = points;
        this.used = used;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "Coupon{" + "id=" + id + ", points=" + points + ", used=" + used + '}';
    }
}
