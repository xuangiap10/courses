package com.cs473.foodiepal.TabBlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs473.foodiepal.databinding.FragmentBlogItemBinding

class BlogAdapter(private var listener: OnViewBlogListener, private var blogs: MutableList<Blog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {
    private lateinit var binding: FragmentBlogItemBinding

    interface OnViewBlogListener {
        fun onViewBlog(blog: Blog)
    }

    inner class BlogViewHolder(binding: FragmentBlogItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlogAdapter.BlogViewHolder {
        binding =
            FragmentBlogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogAdapter.BlogViewHolder, position: Int) {
        binding.tvTitle.text = blogs[position].name
        binding.tvDesc.text = blogs[position].desc
        binding.cvBlogItem.setOnClickListener {
            listener?.onViewBlog(blogs[position])
        }
    }

    override fun getItemCount() = blogs.size
}