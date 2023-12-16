package com.cs473.foodiepal.TabAboutMe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cs473.foodiepal.R
import com.cs473.foodiepal.databinding.FragmentAboutMeBinding
import com.cs473.foodiepal.TabRecipes.UserData
import com.google.android.material.snackbar.Snackbar

class AboutMeFragment : Fragment(), AboutMeDialog.OnAddDetailListener {
    private lateinit var tvNewDetail:TextView
    private lateinit var binding: FragmentAboutMeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about_me, container, false)
        binding = FragmentAboutMeBinding.bind(view)

        binding.ivMe.setImageResource(UserData.userAccount.aboutMe.imgId)
        binding.tvName.text = "So this is me: ${UserData.userAccount.aboutMe.name}"
        binding.fabAboutMeAdd.setOnClickListener {onAboutMeAdd()}
        viewDetails()
        return binding.root
    }

    private fun viewDetails(){
        for(i in 1..UserData.userAccount.aboutMe.details.size) {
            onAddDetail(UserData.userAccount.aboutMe.details[i-1])
        }
    }

    private fun onAddDetail(detail: String) {
        tvNewDetail = TextView(context)
        tvNewDetail.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        tvNewDetail.text = "   $detail\n"
        tvNewDetail.textSize = 18f // Set text size as needed
        binding.llAmDetails.addView(tvNewDetail)
    }

    override fun onDetailAdd(detail: String) {
        UserData.userAccount.aboutMe.details.add(detail)
        onAddDetail(detail)
        //Toast.makeText(context, "New detail is added", Toast.LENGTH_SHORT).show()
    }

    private fun onAboutMeAdd() {
        val dialog = AboutMeDialog()
        dialog.setTargetFragment(this,0)
        dialog.show(parentFragmentManager, AboutMeDialog::class.java.name)
    }
}