package com.example.food_app.model;

public class User {
    private String id;
    private String email;
    private String photoUrl;
    private String contact;
    private String date;
    private String paymentMethod;

    public User(String id, String email, String photoUrl, String contact, String date, String paymentMethod) {
        this.id = id;
        this.email = email;
        this.photoUrl = photoUrl;
        this.contact = contact;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
