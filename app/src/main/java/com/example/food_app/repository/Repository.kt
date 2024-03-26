package com.example.food_app.repository

import com.example.food_app.R
import com.example.food_app.model.Food

class Repository {
    companion object {
        private var listFood: MutableList<Food> = ArrayList()
        fun listFood(): MutableList<Food> {

            listFood.add(
                Food(
                    1,"Pizza gà",1231.0,"Pizza","con","Pizza gà có lẽ vô cùng quen thuộc với dân nghiện pizza bởi tính phổ thông và dễ ăn của loại pizza này." +
                            " Thịt gà là một món ăn bổ dưỡng, giúp gia tăng vị giác của bất cứ món ăn nào mà chúng góp mặt. " +
                            "Đặc biệt những bạn đam mê gà thì không thể bỏ qua chiếc bá", R.drawable.pizzaga,10)
            )

            return listFood
        }
    }
}