package com.example.food_app.repository

import com.example.food_app.model.Food

class Repository {
    private var listFood: MutableList<Food> = ArrayList()
    fun listFood(): MutableList<Food> {

//        listFood.add(
//            Food(
//
//            )
//        )

        return listFood
    }
}