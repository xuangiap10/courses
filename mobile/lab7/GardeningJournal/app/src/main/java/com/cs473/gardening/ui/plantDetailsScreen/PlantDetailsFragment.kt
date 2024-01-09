package com.cs473.gardening.ui.plantDetailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cs473.gardening.databinding.FragmentPlantDetailsBinding
import com.cs473.gardening.db.Plant
import com.cs473.gardening.ui.BaseFragment

class PlantDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentPlantDetailsBinding
    private lateinit var viewModel: PlantDetailsViewModel
    private var curPlantID: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlantDetailsBinding.inflate(inflater, container, false)
        binding.btnDelete.setOnClickListener { onDeletePlant() }

        curPlantID = arguments?.getInt("plantID") ?: 0

        viewModel = ViewModelProvider(this)[PlantDetailsViewModel::class.java]
        viewModel.getPlantByID(curPlantID).observe(viewLifecycleOwner) {
            viewPlantDetails(it)
        }

        return binding.root
    }

    private fun viewPlantDetails(plant: Plant) {
        binding.tvPlantName.text = plant.name
        binding.tvType.text = "Type: ${plant.type}"
        binding.tvWaterFreq.text = "Watering Frequency: ${plant.wateringFrequency} hours"
        binding.tvDate.text = "Planting Date: ${plant.plantingDate}"
    }

    private fun onDeletePlant() {
        viewModel.delete(curPlantID)
        gotoGardenLog()
    }

    private fun gotoGardenLog() {
        val direction =
            com.cs473.gardening.ui.plantDetailsScreen.PlantDetailsFragmentDirections.actionPlantDetailsToGardenLog()
        findNavController().navigate(direction)
    }
}