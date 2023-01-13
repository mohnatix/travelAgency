package com.my.db.dto;

import java.io.Serializable;

public class UserOrderDTO implements Serializable {

    private int id;
    private int userId;
    private int tourId;
    private String userName;
    private String userSurname;
    private String userEmail;
    private String tourName;
    private double priceFixed;
    private int discountFixed;
    private String orderStatus;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
