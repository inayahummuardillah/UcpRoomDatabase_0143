package com.example.ucp2.data.repository

import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {
    suspend fun insertDosen(dosen: Dosen)
    fun getAllDosen(): Flow<List<Dosen>>

    suspend fun deleteDosen(Dosen: Dosen)
    fun getDosen(nidn: String): Flow<Dosen>
}
