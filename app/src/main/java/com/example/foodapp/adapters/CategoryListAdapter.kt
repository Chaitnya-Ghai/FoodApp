package com.example.foodapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.dataClasses.Category
import com.example.foodapp.databinding.CategoryListBinding

class CategoryListAdapter(
    private val context: Context,
    private val list: MutableList<Category>,
    private val categoryInterface: CategoryInterface) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {
    class ViewHolder(val binding: CategoryListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]  // Get the current category object
        // Load image
        Glide.with(context)
            .load(category.strCategoryThumb)
            .into(holder.binding.imgCategory)
        // Set text
        holder.binding.tvCategoryName.text = category.strCategory
        // Set click listener
        holder.binding.cvCategoryListMain.setOnClickListener {
            categoryInterface.onCategoryClick(category.strCategory)  // Pass the actual ID
        }
    }

}
