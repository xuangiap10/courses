package com.cs473.gardening.ui.gardenLogScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cs473.gardening.db.Plant
import com.cs473.gardening.db.PlantDao
import com.cs473.gardening.db.PlantDatabase
import kotlinx.coroutines.launch

class GardenLogViewModel(app: Application) : AndroidViewModel(app) {
    private val plantDao: PlantDao
    var isInitData: Boolean = false
    val allPlants: LiveData<List<Plant>>

    init {
        plantDao = PlantDatabase(app).getPlantDao()
        allPlants = plantDao.getAllPlants()
    }

    fun addPlant(plant: Plant) = viewModelScope.launch {
        plantDao.insert(plant)
    }
/*
    fun deleteAll() = viewModelScope.launch {
        plantDao.deleteAll()
    }
 */
}