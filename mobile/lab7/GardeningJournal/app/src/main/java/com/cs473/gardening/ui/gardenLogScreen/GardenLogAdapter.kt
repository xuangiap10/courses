package com.cs473.gardening.ui.gardenLogScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs473.gardening.databinding.FragmentPlantItemBinding
import com.cs473.gardening.db.Plant

class GardenLogAdapter(private val plantItemCb: OnPlantClick): RecyclerView.Adapter<GardenLogAdapter.PlantViewHolder>() {
    private var plantList: MutableList<Plant> = mutableListOf()

    interface OnPlantClick{
        fun onPlantClickListener(plantID: Int)
    }
    private lateinit var binding: FragmentPlantItemBinding

    class PlantViewHolder(private val viewBinding: FragmentPlantItemBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantViewHolder {
        binding = FragmentPlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        with(plantList[position]) {
            binding.tvName.text = this.name
            binding.cvPlantItem.setOnClickListener {
                plantItemCb.onPlantClickListener(this.id)
            }
        }
    }

    override fun getItemCount(): Int = plantList.size

    fun setDataList(newPlantList: List<Plant>) {
        plantList.clear()
        plantList.addAll(newPlantList)
        notifyDataSetChanged()
    }
}