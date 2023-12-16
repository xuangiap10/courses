package com.cs473.foodiepal.TabBlog

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cs473.foodiepal.databinding.DialogBlogBinding


class BlogDialog() : DialogFragment() {

    interface OnSaveBlogListener {
        fun onSaveBlog(blog: Blog)
    }

    private lateinit var binding: DialogBlogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a new dialog
        val dialog = super.onCreateDialog(savedInstanceState)

        binding = DialogBlogBinding.inflate(layoutInflater)
        // Set the content view
        dialog.setContentView(binding.root)

        // Calculate the width as 70% of the parent width
        val parentWidth = resources.displayMetrics.widthPixels
        val width = (parentWidth * 0.9).toInt()

        // Set the width and height of the dialog
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.btnSave.setOnClickListener { onSave() }
        binding.btnCancel.setOnClickListener { dismiss() }

        return dialog
    }

    private fun onSaveValid(): Boolean {
        if (binding.edtBlogName.text.isEmpty()) {
            Toast.makeText(context, "Blog name is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.edtBlogDesc.text.isEmpty()) {
            Toast.makeText(context, "Blog description is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (binding.edtBlogUrl.text.isEmpty()) {
            Toast.makeText(context, "Blog URL is empty!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun onSave() {
        if (!onSaveValid()) return
        val blogName = binding.edtBlogName.text.toString()
        val blogDesc = binding.edtBlogDesc.text.toString()
        val blogUrl = binding.edtBlogUrl.text.toString()

        val listener = targetFragment as? OnSaveBlogListener
        listener?.onSaveBlog(Blog(blogName,blogDesc,blogUrl))

        dismiss()
    }

}