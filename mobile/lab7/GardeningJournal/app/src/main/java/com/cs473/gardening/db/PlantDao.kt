package com.cs473.gardening.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY id DESC")
    fun getAllPlants(): LiveData<List<Plant>>

    @Insert
    suspend fun insert(plant: Plant)

    @Update
    suspend fun update(plant: Plant)

    @Query("DELETE FROM plants")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM plants WHERE id = :plantId")
    suspend fun delete(plantId: Int)

    @Query("SELECT * FROM plants WHERE id = :plantId")
    fun getPlantById(plantId: Int): LiveData<Plant>
}