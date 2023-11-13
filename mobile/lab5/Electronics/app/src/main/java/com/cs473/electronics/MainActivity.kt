package com.cs473.electronics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs473.electronics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProductInterface {
    private lateinit var binding: ActivityMainBinding
    private var myCart = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewCart.setOnClickListener{onViewCartClick()}

        binding.rcvProducts.layoutManager = LinearLayoutManager(this)
        binding.rcvProducts.adapter = ItemListAdapter(this, this, getProducts())

    }

    private fun getProducts(): ArrayList<Product> {
        val products = ArrayList<Product>()
        products.add(Product("Clothes", "NorthFace Padding", 150.0f, R.drawable.cate_clothes))
        products.add(Product("Lips", "Chanel Lip", 50.0f, R.drawable.cate_beauty))
        products.add(Product("Electric", "IPhone 15 Pro", 999.0f, R.drawable.cate_electric))
        products.add(Product("Food", "Vegetables", 10.8f, R.drawable.cate_food))
        return products

    }

    private fun onViewCartClick(){
        var cartItems = ArrayList<String>()
        myCart.forEach {cartItems += it.name}
        Toast.makeText(this, "You have $cartItems in your cart", Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(p: Product) {
        myCart.add(p)
        Toast.makeText(this,"${p.name} is added to cart", Toast.LENGTH_SHORT).show()
    }
}