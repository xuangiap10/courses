package com.cs473.foodiepal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cs473.foodiepal.TabAboutMe.AboutMeFragment
import com.cs473.foodiepal.TabBlog.BlogFragment
import com.cs473.foodiepal.TabContact.ContactFragment
import com.cs473.foodiepal.TabMealPlanner.MealFragment
import com.cs473.foodiepal.TabRecipes.RecipesFragment

class HomePageAdapter (frgActivity: FragmentActivity) : FragmentStateAdapter (frgActivity){
    override fun getItemCount() = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecipesFragment()
            1 -> MealFragment()
            2 -> BlogFragment()
            3 -> ContactFragment()
            4 -> AboutMeFragment()
            else -> Fragment()
        }
    }
}