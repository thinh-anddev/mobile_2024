package com.example.food_app.model;

public class Cart {
    private Food food;
    private int number;
    private boolean isCheck;

    public Cart(){}

    public Cart(Food food, int number) {
        this.food = food;
        this.number = number;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
