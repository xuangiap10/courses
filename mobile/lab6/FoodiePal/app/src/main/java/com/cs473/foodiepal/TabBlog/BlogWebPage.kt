package com.cs473.foodiepal.TabBlog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.cs473.foodiepal.databinding.ActivityBlogWebpageBinding

class BlogWebPage : AppCompatActivity() {
    private lateinit var binding: ActivityBlogWebpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlogWebpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener{ finish()}
        initWebView()
    }

    private fun initWebView(){
        val url = intent.getStringExtra("url")?:""
        binding.wvBlog.settings.javaScriptEnabled = true
        binding.wvBlog.settings.builtInZoomControls = true
        binding.wvBlog.webViewClient = WebViewClient()
        binding.wvBlog.loadUrl(url)
    }
}