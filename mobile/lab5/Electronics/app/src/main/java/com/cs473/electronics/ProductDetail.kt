package com.cs473.electronics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cs473.electronics.databinding.ActivityProductDetailBinding

class ProductDetail : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("product")) {
            val product = intent.getSerializableExtra("product") as? Product ?: return
            binding.ivDetail.setImageResource(product.imgId)
            binding.tvDetailDesc.text = product.desc
            binding.tvDetailName.text = product.name
            binding.tvDetailCost.text = "$${product.cost.toString()}"
        }
    }

}