package com.example.foodapp.retrofit

import com.example.foodapp.dataClasses.AreaMealList
import com.example.foodapp.dataClasses.CategoryList
import com.example.foodapp.dataClasses.MealsByCategoryList
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
    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") category:String):Call<MealsByCategoryList>

}