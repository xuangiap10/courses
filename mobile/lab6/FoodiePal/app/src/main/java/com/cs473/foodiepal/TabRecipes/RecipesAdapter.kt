package com.cs473.foodiepal.TabRecipes

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs473.foodiepal.databinding.FragmentRecipesItemBinding


class RecipesAdapter(private var recipes: MutableList<Recipes>) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    private lateinit var binding: FragmentRecipesItemBinding
    inner class RecipesViewHolder(recipesView: FragmentRecipesItemBinding): RecyclerView.ViewHolder(recipesView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        binding = FragmentRecipesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecipesViewHolder(binding)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        with(holder) {
            with(recipes[position]) {
                binding.ivRecipe.setImageResource(this.imgId)
                binding.tvName.text = this.name
                binding.tvIngd.text = "with ${this.ingredients}"
                binding.tvCookingTime.text = "${this.cookingTime} mins"
                binding.ratingBar.rating = this.rating

                Log.i("[Giap] Recipe ", "id ${this.id}")
            }
        }
    }
}