package com.example.foodapp.retrofit

import com.example.foodapp.dataClasses.AreaMealList
import com.example.foodapp.dataClasses.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id :String):Call<MealList>
//    getMealById
    @GET("filter.php?")
    fun getMealsByCountry(@Query("a") nationality:String):Call<AreaMealList>
//    getMealsByCategory
}