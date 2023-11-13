package com.cs473.electronics

import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cs473.electronics.databinding.SingleItemBinding

class ItemListAdapter(private var context: Context, private var pContainer: ProductInterface, private var itemList:ArrayList<Product>) : RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>(){

    private lateinit var binding: SingleItemBinding
    inner class ItemListViewHolder(itemView: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        with(holder) {
            with (itemList[position]){
                binding.tvProductName.text = this.name
                binding.tvProductDesc.text = this.desc
                binding.tvProductCost.text = "$${this.cost.toString()}"
                binding.ivProduct.setImageResource(this.imgId)
                binding.btnAdd.setOnClickListener{pContainer.onItemClick(this)}
            }
        }
        binding.layoutItem.setOnClickListener{
            val intent = Intent(context, ProductDetail::class.java)
            intent.putExtra("product", itemList[position])
            context.startActivity(intent)
        }
    }


}