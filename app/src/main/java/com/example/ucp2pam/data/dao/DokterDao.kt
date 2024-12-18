package com.example.ucp2pam.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.Dokter

interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter")
    suspend fun getAllDokter(): List<Dokter>

    @Update
    suspend fun updateDokter(dokter: Dokter)

    @Delete
    suspend fun deleteDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter WHERE idDokter = :id")
    suspend fun getDetailDokter(id: Int): Dokter
}