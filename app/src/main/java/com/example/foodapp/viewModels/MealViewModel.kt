package com.example.foodapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.dataClasses.Meal
import com.example.foodapp.dataClasses.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel() :ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                    Log.d("DEBUG", "Meal name: ${response.body()!!.meals[0].strMeal}")
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("DEBUG", t.message.toString())
            }
        })
    }
    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
}