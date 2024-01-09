package com.cs473.gardening.ui.plantDetailsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cs473.gardening.db.Plant
import com.cs473.gardening.db.PlantDao
import com.cs473.gardening.db.PlantDatabase
import kotlinx.coroutines.launch

class PlantDetailsViewModel(app: Application) : AndroidViewModel(app) {
    private val plantDao: PlantDao

    init {
        plantDao = PlantDatabase(app).getPlantDao()
    }

    fun getPlantByID(plantID: Int): LiveData<Plant> {
        return plantDao.getPlantById(plantID)
    }

    fun delete(plantID: Int) = viewModelScope.launch {
        plantDao.delete(plantID)
    }
}