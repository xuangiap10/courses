package com.cs473.dinnerdecider

import com.cs473.dinnerdecider.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private var foodList = arrayOf("Hamburger", "Pizza", "Mexican", "American", "Chinese")
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddFood.setOnClickListener{addFood()}
        binding.btnDecide.setOnClickListener{decideFood()}
    }

    private fun addFood(){
        val newFood: String = binding.edtNewFood.text.toString()
        if(!foodList.contains(newFood)){
            foodList += newFood
        }
    }

    private fun decideFood(){
        binding.tvFood.text = foodList[Random.nextInt(0,foodList.size)]
    }
}
