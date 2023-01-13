package com.my.db.entity;

import java.io.Serializable;

public class Tour implements Serializable {
    private int id;
    private String name;
    private int tourTypeId;
    private double price;
    private int personsNumber;
    private int duration;
    private int accommodationId;
    private String imageName;
    private int tourStatusId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTourTypeId() {
        return tourTypeId;
    }

    public void setTourTypeId(int tourTypeId) {
        this.tourTypeId = tourTypeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPersonsNumber() {
        return personsNumber;
    }

    public void setPersonsNumber(int personsNumber) {
        this.personsNumber = personsNumber;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getTourStatusId() {
        return tourStatusId;
    }

    public void setTourStatusId(int tourStatusId) {
        this.tourStatusId = tourStatusId;
    }
}
