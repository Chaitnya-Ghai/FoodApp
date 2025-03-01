package com.example.foodapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.dataClasses.LocalMeals
import com.example.foodapp.databinding.MostPopularCardBinding

class MostPopularAdapter(var context: Context, var list: MutableList<LocalMeals> , var mostPopularItemInterface: MostPopularItemInterface):RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {
    class ViewHolder(val binding: MostPopularCardBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularAdapter.ViewHolder {
        val view = MostPopularCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MostPopularAdapter.ViewHolder, position: Int) {
        Glide.with(context)
            .load(list[position].strMealThumb)
            .into(holder.binding.imgPopularMeal)
        holder.binding.imgPopularMeal.setOnClickListener { mostPopularItemInterface.onClick(list[position].idMeal) }
    }
}