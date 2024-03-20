package com.example.food_app.model;

public class Category {
    private String title;
    private boolean isCheck;

    public Category(String title, boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
