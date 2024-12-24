package com.example.ucp2pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal

@Database(entities = [Dokter::class,Jadwal::class], version = 1, exportSchema = false)
abstract class PrakdokDatabase : RoomDatabase(){
    //aksess database
    abstract fun DokterDao(): DokterDao
    abstract fun JadwalDao(): JadwalDao

    companion object{
        @Volatile // nilai variable instance selalu sama
        private var Instance:PrakdokDatabase? = null

        fun getDatabase(context: Context): PrakdokDatabase{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    PrakdokDatabase::class.java, //class database
                    "RsDatabase" //nama database
                )
                    .build().also { Instance = it }
            })
        }
    }
}