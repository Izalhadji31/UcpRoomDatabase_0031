package com.example.ucp2pam.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2pam.data.entity.Jadwal

interface JadwalDao {
    @Insert
    suspend fun insertJadwal(jadwal: Jadwal)

    @Query("SELECT * FROM jadwal")
    suspend fun getAllJadwalr(): List<Jadwal>
}