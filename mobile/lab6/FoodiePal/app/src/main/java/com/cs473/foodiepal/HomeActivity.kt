package com.cs473.foodiepal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cs473.foodiepal.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTabs()
        initBottomMenu()
    }

    private fun initTabs(){
        binding.vp2.adapter = HomePageAdapter(this)
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(binding.tabLayout, binding.vp2) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.menu_recipes)
                1 -> tab.text = resources.getString(R.string.menu_meal)
                2 -> tab.text = resources.getString(R.string.menu_blog)
                3 -> tab.text = resources.getString(R.string.menu_contact)
                4 -> tab.text = resources.getString(R.string.menu_about_me)
            }
        }.attach()
    }

    private fun initBottomMenu() {
        binding.btNavView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.menu_recipes -> binding.vp2.currentItem = 0
                R.id.menu_mealPlanner -> binding.vp2.currentItem = 1
                R.id.menu_blog -> binding.vp2.currentItem = 2
            }
            true
        }
    }

}