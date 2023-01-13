package com.my.db.entity;

import java.io.Serializable;

public class UserOrder implements Serializable {
    private int id;
    private int userId;
    private int tourId;
    private double priceFixed;
    private int discountFixed;
    private int orderStatusId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public double getPriceFixed() {
        return priceFixed;
    }

    public void setPriceFixed(double priceFixed) {
        this.priceFixed = priceFixed;
    }

    public int getDiscountFixed() {
        return discountFixed;
    }

    public void setDiscountFixed(int discountFixed) {
        this.discountFixed = discountFixed;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }
}
