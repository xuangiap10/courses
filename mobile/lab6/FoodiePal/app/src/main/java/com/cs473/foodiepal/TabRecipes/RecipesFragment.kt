package com.cs473.foodiepal.TabRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs473.foodiepal.R
import com.cs473.foodiepal.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment(), RecipesDialog.OnAddRecipeListener {

    private lateinit var binding: FragmentRecipesBinding
    private lateinit var recipeAdapter: RecipesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_recipes, container, false)
        binding = FragmentRecipesBinding.bind(view)

        binding.fabRecipeAdd.setOnClickListener{onClickFab()}

        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        recipeAdapter =  RecipesAdapter(UserData.userAccount.recipes)
        binding.rvRecipes.adapter = recipeAdapter

        return binding.root
    }

    private fun onClickFab() {
        val dialog = RecipesDialog()
        dialog.setTargetFragment(this,0)
        dialog.show(parentFragmentManager, RecipesDialog::class.java.name)
    }

    override fun onRecipeAdd(recipe: Recipes) {
        UserData.userAccount.recipes.add(recipe)
        recipeAdapter.notifyItemInserted(UserData.userAccount.recipes.size-1)
        //recipeAdapter.notifyDataSetChanged()
        //binding.rvRecipes.adapter = RecipesAdapter(recipes)
        //binding.rvRecipes.invalidate()

    }

    override fun onRecipeRemove() {
        UserData.userAccount.recipes.removeAt(UserData.userAccount.recipes.size-1)
        recipeAdapter.notifyDataSetChanged()
    }
}