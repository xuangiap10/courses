package edu.miu.tablelayout

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import com.google.android.material.tabs.TabLayout.Tab
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addRow(view: View) {
        val version = tvVersion.text.toString()
        val name = tvName.text.toString()

        val layoutVersion = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 0, 8, 15)
        }
        val tvVersion = TextView(this)
        tvVersion.setPadding(15)
        tvVersion.setBackgroundResource(R.color.purple_700)
        tvVersion.setTextColor(Color.parseColor("#FFFFFFFF"))
        tvVersion.text = version
        tvVersion.layoutParams = layoutVersion

        val tvName = TextView(this)
        tvName.setPadding(15)
        tvName.setBackgroundResource(R.color.purple_700)
        tvName.setTextColor(Color.parseColor("#FFFFFFFF"))
        tvName.text = name

        val tableRow = TableRow(this);

        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT)
        tableRow.layoutParams = layoutParams

        tableRow.addView(tvVersion, 0)
        tableRow.addView(tvName, 1)
        tbView.addView(tableRow)
    }
}