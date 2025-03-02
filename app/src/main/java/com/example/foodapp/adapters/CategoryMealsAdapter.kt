package com.example.foodapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.dataClasses.MealsByCategory
import com.example.foodapp.databinding.MealItemBinding

class CategoryMealsAdapter(private val context: Context, private var list: List<MealsByCategory>) : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {

    class ViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MealItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvFavMealName.text = list[position].strMeal
        Glide.with(context).load(list[position].strMealThumb).into(holder.binding.imgFavMeal)
    }

    fun updateList(newList: List<MealsByCategory>) {
        list = newList
        notifyDataSetChanged() // Refresh without creating a new adapter
    }
}
