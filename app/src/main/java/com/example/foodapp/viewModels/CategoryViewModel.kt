package com.example.foodapp.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.dataClasses.MealsByCategory
import com.example.foodapp.dataClasses.MealsByCategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : ViewModel() {
    private val categoryMealLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getCategoryMeals(categoryId: String) {
        RetrofitInstance.api.getMealsByCategory(categoryId).enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealList ->
                    Log.e("mealList", "mealList =: ${mealList}")
                    categoryMealLiveData.postValue(mealList.meals ?: emptyList())
                } ?: run {
                    Log.e("meals", "No response from API")
                    categoryMealLiveData.postValue(emptyList()) //Ensure LiveData is updated
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryViewModel", "Failure: ${t.message}")
            }
        })
    }

    fun observeCategoryMealsLiveData(): LiveData<List<MealsByCategory>> = categoryMealLiveData
}
