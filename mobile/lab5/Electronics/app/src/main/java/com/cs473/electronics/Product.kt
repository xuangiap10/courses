package com.cs473.electronics

import java.io.Serializable

data class Product(val name: String, val desc: String, val cost: Float, val imgId: Int) : Serializable {
    override fun toString(): String {
        return "Product(name: $name, desc: $desc, cost: $cost, imgId: $imgId)"
    }
}