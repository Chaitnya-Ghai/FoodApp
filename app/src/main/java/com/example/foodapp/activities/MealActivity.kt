package com.example.foodapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.viewModels.MealViewModel


class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm : MealViewModel
    lateinit var youtubeLink :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]
        startLoading()
        val mealId = intent.getStringExtra("mealId")// Get data from Intent
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mealMvvm.getMealDetail(mealId.toString())
        mealMvvm.observeMealDetailsLiveData().observe(this) { result ->
            hideLoading()
            binding.collapsingToolbarLayout.title = result.strMeal
            Glide.with(applicationContext)
                .load(result.strMealThumb)
                .into(binding.imgMealDetail)
            youtubeLink= result.strYoutube
            binding.tvCategoryInfo.text = buildString {
                append("Category : ")
                append(result.strCategory)
            }
            binding.tvAreaInfo.text = buildString {
                append("Area : ")
                append(result.strArea)
            }
            binding.tvContent.text = result.strIngredient1
            binding.tvInstructions.text = result.strInstructions
            Log.d("DEBUG", "Meal name: ${result.strMeal}")
        }
        binding.imgYoutube.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)).also { startActivity(it) }
        }
    }
    private fun hideLoading() {
        binding.apply {
            loadingGifMeals.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
            main.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
        }
    }
    private fun startLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            loadingGifMeals.visibility = View.VISIBLE
            main.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.g_loading))
        }
    }
}