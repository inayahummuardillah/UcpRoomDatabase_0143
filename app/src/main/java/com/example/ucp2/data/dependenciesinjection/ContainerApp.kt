package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.KrsDatabase
import com.example.ucp2.data.repository.LocalRepositoryDosen
import com.example.ucp2.data.repository.RepositoryDosen
import com.example.ucp2.data.repository.RepositoryMatakuliah
import com.example.ucp2.repository.LocalRepositoryMatakuliah

interface InterfaceContainerApp{
    val repositoryDosen: RepositoryDosen
    val repositoryMatakuliah: RepositoryMatakuliah
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    // Repository Dosen
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).DosenDao())
    }

    override val repositoryMatakuliah: RepositoryMatakuliah by lazy {
        LocalRepositoryMatakuliah(KrsDatabase.getDatabase(context).MatakuliahDao())
    }
}