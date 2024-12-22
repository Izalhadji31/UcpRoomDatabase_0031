package com.example.ucp2pam.data.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter ORDER BY idDokter ASC ")
    suspend fun getAllDokter(): Flow<List<Dokter>>
}