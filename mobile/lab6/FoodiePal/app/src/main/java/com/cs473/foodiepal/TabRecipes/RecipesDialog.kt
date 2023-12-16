package com.cs473.foodiepal.TabRecipes

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.cs473.foodiepal.R
import com.cs473.foodiepal.databinding.DialogRecipeBinding


class RecipesDialog: DialogFragment() {
    interface OnAddRecipeListener {
        fun onRecipeAdd(recipe: Recipes)
        fun onRecipeRemove()
    }
    private lateinit var binding: DialogRecipeBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new dialog
        val dialog = super.onCreateDialog(savedInstanceState)

        binding = DialogRecipeBinding.inflate(layoutInflater)
        // Set the content view
        dialog.setContentView(binding.root)

        // Calculate the width as 70% of the parent width
        val parentWidth = resources.displayMetrics.widthPixels
        val width = (parentWidth * 0.9).toInt()

        // Set the width and height of the dialog
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.btnAdd.setOnClickListener{onAddExec()}
        binding.btnCancel.setOnClickListener{onCancel()}

        return dialog
    }

    private fun onCancel() {
        dismiss()
    }

    private fun onAddValid(): Boolean {
        if(binding.tvNewName.text.isEmpty()) {
            Toast.makeText(context, "Recipe name is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.tvNewIngd.text.isEmpty()) {
            Toast.makeText(context, "Recipe ingredient is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        if(binding.tvNewCookingTime.text.isEmpty()) {
            Toast.makeText(context, "Recipe cooking time is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun onAdd() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm Message")
        builder.setMessage("Do you want to add this recipes?")
        builder.setIcon(android.R.drawable.star_big_on)
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            onAddExec()
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun onAddExec() {
        if(!onAddValid()) return
        val name = binding.edtNewName.text.toString()
        val ingr = binding.edtNewIngd.text.toString()
        val cookingTime = binding.edtNewCookingTime.text.toString().toInt()
        val rating = binding.newRating.rating
        val imgId = resources.getIdentifier("recipedefault", "drawable", activity?.packageName)

        val listener = targetFragment  as? OnAddRecipeListener
        listener?.onRecipeAdd(Recipes(UserData.userAccount.recipes.size+1,name,ingr,cookingTime,rating, imgId))
         dismiss()
    }

}