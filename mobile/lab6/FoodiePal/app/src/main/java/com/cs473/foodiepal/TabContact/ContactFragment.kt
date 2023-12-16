package com.cs473.foodiepal.TabContact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cs473.foodiepal.R
import com.cs473.foodiepal.databinding.FragmentContactBinding

class ContactFragment : Fragment() {
    private lateinit var binding: FragmentContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        binding = FragmentContactBinding.bind(view)
        binding.btnCallUs.setOnClickListener{onCallUs()}
        binding.btnEmailUs.setOnClickListener{onEmailUs()}

        return binding.root
    }

    private fun onCallUs() {
        val phoneNumber = resources.getString(R.string.ct_phone)
        val uri = Uri.parse("tel:$phoneNumber");
        val it = Intent(Intent.ACTION_DIAL, uri);
        startActivity(it);
    }

    private fun onEmailUs() {
        val email = resources.getString(R.string.ct_email)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")

        if (intent.resolveActivity(requireActivity().packageManager) != null)
            startActivity(intent)
        else {
            // No email app available, open a web browser to a webmail service
            val webMailIntent = Intent(Intent.ACTION_VIEW)
            webMailIntent.data = Uri.parse("https://mail.google.com")
            startActivity(webMailIntent)
        }

    }
}