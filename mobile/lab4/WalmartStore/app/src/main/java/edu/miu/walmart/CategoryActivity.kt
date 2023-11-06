package edu.miu.walmart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val user = intent.getSerializableExtra("user") as User?
        lblWelcome.text = "Welcome: ${user?.username}"
    }

    fun showFood(view: View) {
        Toast.makeText(this, "Food", Toast.LENGTH_LONG).show()
    }

    fun showBeauty(view: View) {
        Toast.makeText(this, "Beauty", Toast.LENGTH_LONG).show()
    }

    fun showClothes(view: View) {
        Toast.makeText(this, "Clothes", Toast.LENGTH_LONG).show()
    }

    fun showElectric(view: View) {
        Toast.makeText(this, "Electric", Toast.LENGTH_LONG).show()
    }
}