package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dosen
import java.util.concurrent.Flow

@Dao
interface DosenDao {
    //fungsi get all data
    @Query("select * from dosen")
    fun getAllDosen() : Flow<List<Dosen>>

    @Insert
    suspend fun insertDosen(
        dosen: Dosen
    )
}