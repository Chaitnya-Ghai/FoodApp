package com.example.foodapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.dataClasses.AreaMealList
import com.example.foodapp.dataClasses.CategoryList
import com.example.foodapp.dataClasses.LocalMeals
import com.example.foodapp.dataClasses.Meal
import com.example.foodapp.dataClasses.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<LocalMeals>?>()
    private var categoryLiveData = MutableLiveData<CategoryList>()

    fun getCategoryData() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.isSuccessful && response.body() != null) {
                    val originalCategories = response.body()?.categories ?: emptyList()
                    val excludedCategories = listOf("Beef", "Chicken", "Pork")
                    val filteredCategories = originalCategories.filter { it.strCategory !in excludedCategories }
                    categoryLiveData.postValue(CategoryList(filteredCategories))
                } else {
                    Log.e("API_RESPONSE", "getCategoryData Response unsuccessful: ${response.errorBody()?.string()}")
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("API_ERROR", "API call failed: ${t.message}")
            }
        })
    }

    fun getPopularMeals(nationality: String) {
        RetrofitInstance.api.getMealsByCountry(nationality).enqueue(object : Callback<AreaMealList> {
            override fun onResponse(call: Call<AreaMealList>, response: Response<AreaMealList>) {
                if (response.isSuccessful && response.body() != null) {
                    val mealList = response.body()?.meals
                    if ( !mealList.isNullOrEmpty() ) {
                        popularItemsLiveData.postValue(mealList)
                    } else {
                        Log.e(" API_RESPONSE", " getMealsByCountry Received empty meals list")
                    }
                } else {
                    Log.e("API_RESPONSE", "getMealsByCountry Response unsuccessful: ${response.errorBody()?.string()}")
                    return
                }
            }

            override fun onFailure(call: Call<AreaMealList>, t: Throwable) {
                Log.e("API_ERROR", "API call failed: ${t.message}")
            }
        })
    }

    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful && response.body() != null) {
                    val mealList = response.body()?.meals
                    if (!mealList.isNullOrEmpty()) {
                        val randomMeal: Meal = mealList[0]
                        randomMealLiveData.postValue(randomMeal)
                        Log.d("Test", "Meal name: ${randomMeal.strMeal}")
                    } else {
                        Log.e("Test", "Received empty meals list")
                    }
                } else {
                    Log.e("Test", "Response unsuccessful or body is null: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeFragment", "API call failed: ${t.message}")
            }
        })
    }

    fun observeCategoryLiveData():LiveData<CategoryList>{
        return categoryLiveData
    }
    fun observePopularItemsLiveData(): MutableLiveData<List<LocalMeals>?> {
        return popularItemsLiveData
    }
    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }
}


// difference b/w live data, mutableLive data
// is ->
    /*
    *mutable = we can change its value
    *but in live data we can't change its value
    *can only read its data
    * */
