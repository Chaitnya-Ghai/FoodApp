package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapters.CategoryInterface
import com.example.foodapp.adapters.CategoryListAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.adapters.MostPopularItemInterface
import com.example.foodapp.dataClasses.Meal
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.viewModels.HomeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), MostPopularItemInterface , CategoryInterface{
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var randomMeal: Meal
    private lateinit var mainActivity: MainActivity
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var homeMvvm: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity= activity as MainActivity
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getPopularMeals("Indian")
        homeMvvm.getRandomMeal()
        homeMvvm.getCategoryData()

        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner){ res->
            val adapter = res?.let { MostPopularAdapter(mainActivity, it.toMutableList(),this) }
            binding.rvMealsPopular.adapter=adapter
            binding.rvMealsPopular.layoutManager=LinearLayoutManager(mainActivity,LinearLayoutManager.HORIZONTAL,false)
            adapter?.notifyDataSetChanged()
        }
        homeMvvm.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(mainActivity)
                .load(value.strMealThumb)
                .into(binding.imgRandomMeal)
            this@HomeFragment.randomMeal = value
        }
        homeMvvm.observeCategoryLiveData().observe(viewLifecycleOwner){ res->
            binding.rvCategory.layoutManager=GridLayoutManager(mainActivity,3)
            val categoryListAdapter = CategoryListAdapter(mainActivity,res.categories.toMutableList(),this)
            binding.rvCategory.adapter=categoryListAdapter
            categoryListAdapter.notifyDataSetChanged()
        }
        binding.imgRandomMeal.setOnClickListener{
            val intent = Intent(mainActivity, MealActivity::class.java)
            intent.putExtra("mealId",randomMeal.idMeal)
            startActivity(intent)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onMealClick(id: String) {
        val intent = Intent(mainActivity, MealActivity::class.java)
        intent.putExtra("mealId",id)
        startActivity(intent)
    }
    override fun onCategoryClick(categoryId: String) {
        Toast.makeText(mainActivity, "categoryId: $categoryId", Toast.LENGTH_SHORT).show()
        val intent = Intent( mainActivity , CategoryMealsActivity::class.java)
        intent.putExtra("categoryId",categoryId)
        startActivity(intent)
    }
}