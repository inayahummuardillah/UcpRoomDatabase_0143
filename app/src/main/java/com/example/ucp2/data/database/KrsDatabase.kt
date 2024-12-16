package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.dao.MatakuliahDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.Matakuliah

//Database untuk dosen
@Database(entities = [Dosen::class], version = 1, exportSchema = false)
abstract class DosenDatabase: RoomDatabase() {

    // mendifinisikan fungsi untuk mengakses data
    abstract fun DosenDao(): DosenDao //fungsinya ngambil dari data class dosen

    companion object{
        @Volatile //memastikan bahwa nilai variabel instance selau sama di semua thread
        private var Instance: DosenDatabase? = null

        fun getDatabase(context: Context): DosenDatabase{
            return (Instance ?: kotlin.synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DosenDatabase::class.java,
                    "DosenDatabase"
                )
                    .build().also { Instance = it }
            })

        }

    }
}

//Database untuk Matakuliah
@Database(entities = [Matakuliah::class], version = 1, exportSchema = false)
abstract class MatakuliahDatabase: RoomDatabase() {

    // mendifinisikan fungsi untuk mengakses data
    abstract fun MatakuliahDao(): MatakuliahDao //fungsinya ngambil dari data class dosen

    companion object{
        @Volatile //memastikan bahwa nilai variabel instance selau sama di semua thread
        private var Instance: MatakuliahDatabase? = null

        fun getDatabase(context: Context): MatakuliahDatabase{
            return (Instance ?: kotlin.synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MatakuliahDatabase::class.java,
                    "MatakuliahDatabase"
                )
                    .build().also { Instance = it }
            })

        }

    }
}
