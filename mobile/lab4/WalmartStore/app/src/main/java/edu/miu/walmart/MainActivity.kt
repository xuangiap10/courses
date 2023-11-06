package edu.miu.walmart

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val users = mutableListOf<User>(
        User("jason", "nguyen", "jason@gmail.com", "123456"),
        User("leo", "nguyen", "leo@gmail.com", "123456"),
        User("jolie", "nguyen", "jolie@gmail.com", "123456"),
        User("peter", "nguyen", "peter@gmail.com", "123456"),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newUser = intent.getSerializableExtra("user") as User?
        if (newUser != null) {
            users.add(newUser)
        }
    }

    fun signIn(view: View) {
        var email = tvUserName.text.toString().trim()
        var pass = tvPassword.text.toString().trim()

        for (user in users) {
            if (user.username == email && user.password == pass) {
                openCategoryActivity(user)
            }
        }
    }

    fun openCategoryActivity(user: User) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    fun openSignupView(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun openMail(view: View) {
        val username = tvUserName.text.toString().trim()
        lateinit var pass: String
        var existUser = false
        for (user in users) {
            if (user.username == username) {
                pass = user.password
                existUser = true
                break
            }
        }
        if (!existUser) {
            Toast.makeText(this, "You don't have an account with email $username", Toast.LENGTH_LONG).show()
            return
        }
        var messBody = "Your password is $pass"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailTo:")
        intent.putExtra(Intent.EXTRA_EMAIL, username)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Forgot Password from Walmart")
        intent.putExtra(Intent.EXTRA_TEXT, messBody)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}