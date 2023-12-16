package com.cs473.foodiepal.TabMealPlanner

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cs473.foodiepal.R
import com.cs473.foodiepal.databinding.DialogMealBinding
import com.cs473.foodiepal.TabRecipes.UserData
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MealDialog(val plan: MealPlan) : DialogFragment(), AdapterView.OnItemSelectedListener {

    interface onSavePlanListener {
        fun onSavePlan(planDate: Date)
    }
    private lateinit var binding: DialogMealBinding
    private var breakfastID = 0
    private var lunchID = 0
    private var dinnerID = 0
    private lateinit var adapter: ArrayAdapter<String>
    private var recipeNameList: ArrayList<String> = ArrayList<String>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new dialog
        val dialog = super.onCreateDialog(savedInstanceState)

        binding = DialogMealBinding.inflate(layoutInflater)
        // Set the content view
        dialog.setContentView(binding.root)

        // Calculate the width as 70% of the parent width
        val parentWidth = resources.displayMetrics.widthPixels
        val width = (parentWidth * 0.9).toInt()

        // Set the width and height of the dialog
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        initSpinner()

        val currentDayString = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(plan.date)
        binding.edtDate.setText(currentDayString)
        if (plan.mealID.size > 0) {
            binding.spBreakfast.setSelection(plan.mealID[0])
            binding.spLunch.setSelection(plan.mealID[1])
            binding.spDinner.setSelection(plan.mealID[2])
        }
        binding.btnSave.setOnClickListener { onSave() }
        binding.btnCancel.setOnClickListener { onCancel() }
        binding.btnCalendar.setOnClickListener { onCalendar() }

        return dialog
    }

    private fun initSpinner() {
        recipeNameList.add("None")
        UserData.userAccount.recipes.forEach { recipeNameList.add(it.name) }

        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            recipeNameList
        )
        binding.spBreakfast.adapter = adapter;
        binding.spLunch.adapter = adapter
        binding.spDinner.adapter = adapter

        binding.spBreakfast.onItemSelectedListener = this
        binding.spLunch.onItemSelectedListener = this
        binding.spDinner.onItemSelectedListener = this
    }

    private fun onCalendar() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                binding.edtDate.setText("$dayOfMonth/$monthOfYear/$year")

            },
            mYear,
            mMonth,
            mDay
        )

        dpd.show()
    }

    private fun onCancel() {
        dismiss()
    }

    private fun onSaveValid(): Boolean {
        if (binding.edtDate.text.isEmpty()) {
            Toast.makeText(context, "Date is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun onSave() {
        if (!onSaveValid()) return
        val date = Date(binding.edtDate.text.toString())
        val mealPlan = UserData.userAccount.mealPlans.find { it.date == date }
        if (mealPlan == null)
            UserData.userAccount.mealPlans.add(
                MealPlan(date, mutableListOf(breakfastID, lunchID, dinnerID))
            )
        else
            mealPlan.mealID = mutableListOf(breakfastID, lunchID, dinnerID)

        val listener = targetFragment as? onSavePlanListener
        listener?.onSavePlan(date)

        dismiss()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spBreakfast -> breakfastID = position
            R.id.spLunch -> lunchID = position
            R.id.spDinner -> dinnerID = position
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}