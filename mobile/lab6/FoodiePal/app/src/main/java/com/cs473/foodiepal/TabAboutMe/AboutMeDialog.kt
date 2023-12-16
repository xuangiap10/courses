package com.cs473.foodiepal.TabAboutMe

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cs473.foodiepal.databinding.DialogAboutMeBinding


class AboutMeDialog : DialogFragment() {
    interface OnAddDetailListener {
        fun onDetailAdd(detail: String)
    }

    private lateinit var binding: DialogAboutMeBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new dialog
        val dialog = super.onCreateDialog(savedInstanceState)

        binding = DialogAboutMeBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        // Calculate the width as 70% of the parent width
        val parentWidth = resources.displayMetrics.widthPixels
        val width = (parentWidth * 0.9).toInt()

        // Set the width and height of the dialog
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.btnAdd.setOnClickListener {
            val listener = targetFragment as? OnAddDetailListener
            listener?.onDetailAdd(binding.edtNewDetail.text.toString())
            dismiss()
        }
        binding.btnCancel.setOnClickListener { dismiss() }

        return dialog
    }

}