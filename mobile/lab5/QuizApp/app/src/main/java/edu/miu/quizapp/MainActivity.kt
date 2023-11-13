package edu.miu.quizapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private var isSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val clicked = group.findViewById(checkedId) as RadioButton
            isSelected = (clicked == radioButton3)
        }

        checkBox1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBox2.isChecked = false
                checkBox3.isChecked = false
                checkBox4.isChecked = false
            }
        }

        checkBox2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox3.isChecked = false
                checkBox4.isChecked = false
            }
        }

        checkBox3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox2.isChecked = false
                checkBox4.isChecked = false
            }
        }

        checkBox4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBox1.isChecked = false
                checkBox2.isChecked = false
                checkBox3.isChecked = false
            }
        }
    }

    fun reset(view: View) {
        resetAll()
    }

    private fun resetAll() {
        score = 0
        checkBox1.isChecked = false
        checkBox2.isChecked = false
        checkBox3.isChecked = false
        checkBox4.isChecked = false
        if (radioButton1.isChecked) {
            radioButton1.isChecked = false
        }
        if (radioButton2.isChecked) {
            radioButton2.isChecked = false
        }
        if (radioButton3.isChecked) {
            radioButton3.isChecked = false
        }
        if (radioButton4.isChecked) {
            radioButton4.isChecked = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun submit(view: View) {
        if (checkBox2.isChecked) {
            score += 50
        }
        if (isSelected) {
            score += 50
        }
        var curTime = LocalDateTime.now()
        val dateFormmatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
        var date = curTime.format(dateFormmatter)
        val mess = "Congratulations! You submitted on current $date, Your achieved $score%"

        val builder = AlertDialog.Builder(this)
        builder.setMessage(mess)
        builder.setPositiveButton("OK") { dialogShowed, which ->
            dialogShowed.dismiss()
            resetAll()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}