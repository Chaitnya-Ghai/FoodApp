package com.example.foodapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapters.CategoryMealsAdapter
import com.example.foodapp.adapters.MealItemInterface
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.viewModels.CategoryViewModel

class CategoryMealsActivity : AppCompatActivity(), MealItemInterface {
    private val binding by lazy { ActivityCategoryMealsBinding.inflate(layoutInflater) }
    private val categoryId by lazy { intent?.getStringExtra("categoryId") ?: "" }  // Handle null categoryId
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private val categoryMvvm: CategoryViewModel by lazy { ViewModelProvider(this)[CategoryViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        categoryMealsAdapter = CategoryMealsAdapter(this, emptyList(),this)
        binding.mealRv.layoutManager = GridLayoutManager(this, 2)
        binding.mealRv.adapter = categoryMealsAdapter

        categoryMvvm.getCategoryMeals(categoryId)

        categoryMvvm.observeCategoryMealsLiveData().observe(this) {result ->
            binding.tvCategoryCount.text = result.size.toString()
            categoryMealsAdapter.updateList(result)  // Update dynamically
        }
    }

    override fun onMealItemClick(mealId: String) {
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra("mealId",mealId)
        startActivity(intent)
    }
}
