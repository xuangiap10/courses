package com.cs473.gardening.ui.gardenLogScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs473.gardening.databinding.FragmentGardenLogBinding
import com.cs473.gardening.db.Plant
import com.cs473.gardening.ui.BaseFragment

class GardenLogFragment : BaseFragment(), GardenLogAdapter.OnPlantClick,
    AddPlantDialog.OnAddPlantListener {
    private lateinit var binding: FragmentGardenLogBinding
    private lateinit var adapter: GardenLogAdapter
    private lateinit var viewModel: GardenLogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGardenLogBinding.inflate(inflater, container, false)

        binding.fabAdd.setOnClickListener { onAddPlant() }

        binding.rvPlants.layoutManager = LinearLayoutManager(requireContext())
        adapter = GardenLogAdapter(this@GardenLogFragment)

        viewModel = ViewModelProvider(this)[GardenLogViewModel::class.java]

        viewModel.allPlants.observe(viewLifecycleOwner) {
            initData()
            adapter.setDataList(it)
            binding.rvPlants.adapter = adapter

        }

        return binding.root
    }

    override fun onPlantClickListener(plantID: Int) {
        val direction =
            com.cs473.gardening.ui.gardenLogScreen.GardenLogFragmentDirections.actionGardenLogToPlantDetails(
                plantID
            )
        findNavController().navigate(direction)
    }

    private fun onAddPlant() =
        AddPlantDialog(this).show(parentFragmentManager, AddPlantDialog::class.java.name)

    override fun onAddPlant(plant: Plant) {
        viewModel.addPlant(plant)
    }

    private fun initData() {
        if (!viewModel.isInitData) {
            viewModel.allPlants.value?.let {
                if (it.isNotEmpty())
                    return
                viewModel.addPlant(Plant("Rose", "Flower", 2, "2023-01-01"))
                viewModel.addPlant(Plant("Tomato", "Vegetable", 3, "2023-02-15"))
                viewModel.addPlant(Plant("Basil", "Herb", 1, "2023-03-10"))
            }
            viewModel.isInitData = true
        }
    }
}