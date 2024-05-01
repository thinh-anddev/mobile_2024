package com.example.food_app.model;

import androidx.annotation.Keep;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
@TypeConverters(Cheating.class)
public class Cheating implements Serializable {
    @SerializedName("cheating")
    private String cheating;

    public Cheating(String cheating) {
        this.cheating = cheating;
    }

    public String getCheating() {
        return cheating;
    }

    public void setCheating(String cheating) {
        this.cheating = cheating;
    }
}