package com.my.db.dto;

import java.io.Serializable;

public class TourDTO implements Serializable {
    private int id;
    private String name;
    private String tourType;
    private double price;
    private int personsNumber;
    private int duration;
    private String accommodation;
    private String imageName;
    private String tourStatus;

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

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
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

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(String tourStatus) {
        this.tourStatus = tourStatus;
    }
}
