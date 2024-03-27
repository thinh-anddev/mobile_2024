package com.example.food_app.view.splash

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import com.example.food_app.base.BaseActivity
import com.example.food_app.databinding.ActivitySplashBinding
import com.example.food_app.model.Food
import com.example.food_app.repository.Repository
import com.example.food_app.view.user.UserActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var foodList: MutableList<Food>? = ArrayList<Food>()

    override fun setViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        foodList = ArrayList()
        foodList?.addAll(Repository.listFood())
//        if(SharePreferenceUtils.getBoolean(Constant.DATA_PRODUCT,true)) {
//            foodList.addAll(Repository.Companion.listFood());
//            rf.child("Foods").setValue(foodList);
//            SharePreferenceUtils.putBoolean(Constant.DATA_PRODUCT,false);
//        }
        Log.d("thienlyoi", foodList.toString())
    }

    override fun viewListener() {
        binding.btnGetStarted.setOnClickListener {
            startActivity(Intent(this@SplashActivity, UserActivity::class.java))
            finish()
        }
    }
}
