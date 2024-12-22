package com.example.ucp2pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Mendefinisikan fungsi untuk mengakses DAO Dokter dan Jadwal
    abstract fun dokterDao(): DokterDao
    abstract fun jadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "PrakdokDatabase"
                )
                    .build()
                    .also { instance = it }
            }
        }
    }
}