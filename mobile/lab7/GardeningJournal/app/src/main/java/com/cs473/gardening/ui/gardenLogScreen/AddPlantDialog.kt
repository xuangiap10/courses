package com.cs473.gardening.ui.gardenLogScreen

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cs473.gardening.databinding.DialogAddPlantBinding
import com.cs473.gardening.db.Plant
import java.util.Calendar


class AddPlantDialog(private val callback: OnAddPlantListener) : DialogFragment() {
    private lateinit var binding: DialogAddPlantBinding

    interface OnAddPlantListener {
        fun onAddPlant(plant: Plant)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        binding = DialogAddPlantBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        val parentWidth = resources.displayMetrics.widthPixels
        val width = (parentWidth * 0.9).toInt()
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.btnAdd.setOnClickListener { onAdd() }
        binding.btnCancel.setOnClickListener { dismiss() }
        initPlantDate()

        return dialog
    }

    private fun initPlantDate() {
        val cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH)+1
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        binding.edtDate.text = "$mMonth-$mDay-$mYear"

        binding.edtDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    binding.edtDate.text = "$monthOfYear-$dayOfMonth-$year"
                },
                mYear,
                mMonth,
                mDay
            ).show()
        }
    }

    private fun isValidInput(edt: EditText, field: String): Boolean {
        if (edt.text.toString().isNotEmpty()) return true

        Toast.makeText(context, "Please input $field.", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun isValidInputs(): Boolean {
        if (!isValidInput(binding.edtName, "Plant Name"))
            return false
        if (!isValidInput(binding.edtType, "Plant Type"))
            return false
        if (!isValidInput(binding.edtFreq, "Watering Frequency"))
            return false

        return true
    }

    private fun onAdd() {
        if (!isValidInputs()) return

        val name = binding.edtName.text.toString()
        val type = binding.edtType.text.toString()
        val freq = binding.edtFreq.text.toString().toInt()
        val date = binding.edtDate.text.toString()

        callback.onAddPlant(Plant(name, type, freq, date))

        dismiss()
    }



}