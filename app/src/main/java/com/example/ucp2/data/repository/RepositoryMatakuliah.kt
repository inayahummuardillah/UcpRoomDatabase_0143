package com.example.ucp2.data.repository

import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.Matakuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatakuliah {
    suspend fun insertMatakuliah(matakuliah: Matakuliah)
    fun getAllMatakuliah(): Flow<List<Matakuliah>>

    fun getMatakuliah(nidn: String): Flow<Matakuliah>

    suspend fun deleteMatakuliah(Matakuliah: Matakuliah)

    suspend fun updateDosen(Matakuliah: Matakuliah)
    fun get(nidn: String): Flow<Matakuliah>
    suspend fun updateMatakuliah(matakuliah: Matakuliah)
}
