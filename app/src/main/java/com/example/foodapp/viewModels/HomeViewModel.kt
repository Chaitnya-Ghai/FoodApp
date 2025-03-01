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
    private var popularItemsLiveData = MutableLiveData<List<LocalMeals>>()
    private var categoryLiveData = MutableLiveData<CategoryList>()

    fun getCategoryData(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() !=null){
                    val excludedCategories = listOf("Beef" , "Chicken" , "Pork")  // Add more categories here if needed
                    val filteredCategories = response.body()!!.categories.filter { it.strCategory !in excludedCategories }
                    val updatedResponse = response.body()!!.copy(categories = filteredCategories)
                    categoryLiveData.postValue(updatedResponse)
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("DEBUG", t.message.toString())
            }
        })
    }
    fun  getPopularMeals(nationality:String){
        RetrofitInstance.api.getMealsByCountry(nationality).enqueue(object : Callback<AreaMealList>{
            override fun onResponse(call: Call<AreaMealList>, response: Response<AreaMealList>) {
                if (response.body() != null) {
                    popularItemsLiveData.postValue(response.body()!!.meals) // use .postValue() instead of .value to update LiveData safely.
//  If code is already on the main thread, we can use .value(safely);
//  But since Retrofit runs on a background thread, this might cause issues. therefore i use .postValue here
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<AreaMealList>, t: Throwable) {
                Log.d("DEBUG", t.message.toString())
            }
        }
        )
    }
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null){
                    val randomMeal : Meal = response.body()!!.meals[0]
                    randomMealLiveData.value=randomMeal
                    Log.d("Test", "meal name :${randomMeal.strMeal} ")
                }
                else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeFragment", t.message.toString() )
            }
        })
    }
    fun observeCategoryLiveData():LiveData<CategoryList>{
        return categoryLiveData
    }
    fun observePopularItemsLiveData():LiveData<List<LocalMeals>>{
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
