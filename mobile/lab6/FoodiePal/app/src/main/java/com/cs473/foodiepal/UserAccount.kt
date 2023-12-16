package com.cs473.foodiepal

import com.cs473.foodiepal.TabAboutMe.AboutMe
import com.cs473.foodiepal.TabBlog.Blog
import com.cs473.foodiepal.TabMealPlanner.MealPlan
import com.cs473.foodiepal.TabRecipes.Recipes

data class UserAccount(val username: String, val password: String,
                       val recipes: MutableList<Recipes>,
                       val mealPlans: MutableList<MealPlan>,
                       val blogs: MutableList<Blog>,
                       val aboutMe: AboutMe)