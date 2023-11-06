package edu.miu.walmart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun signUp(view: View) {
        var firstName = tvFirstName.text.toString().trim()
        var lastName = tvLastName.text.toString().trim()
        var email = tvEmail.text.toString().trim()
        var pass = tvPass.text.toString().trim()
        val user = User(firstName, lastName, email, pass)
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
            return
        }
        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_LONG).show()
        tvFirstName.text.clear()
        tvLastName.text.clear()
        tvEmail.text.clear()
        tvPass.text.clear()

        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}