package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Matakuliah
import java.util.concurrent.Flow

@Dao
@Dao
interface MatakuliahDao {
    // Fungsi untuk mendapatkan semua data
    @Query("SELECT * FROM MataKuliah")
    fun getAllMatakuliah(): Flow<List<Matakuliah>>


    @Insert
    suspend fun insertMatakuliah(
        matakuliah: Matakuliah
    )
}