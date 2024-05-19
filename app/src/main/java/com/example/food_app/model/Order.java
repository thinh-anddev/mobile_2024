package com.example.food_app.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String nameUser;
    private String address;
    private String contact;
    private String email;
    private String time;
    private List<Cart> foodList;
    private String status;
    private String invoiceNumber;


    public Order(){}

    public Order(String nameUser, String address, String contact, String email, String time, List<Cart> foodList, String status, String invoiceNumber) {
        this.nameUser = nameUser;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.time = time;
        this.foodList = foodList;
        this.status = status;
        this.invoiceNumber = invoiceNumber;
    }



    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Cart> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Cart> foodList) {
        this.foodList = foodList;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
