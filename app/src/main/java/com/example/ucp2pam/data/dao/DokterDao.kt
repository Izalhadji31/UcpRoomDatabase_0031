package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow


@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter ORDER BY namaDokter ASC")
    fun getAllDokter(): Flow<List<Dokter>>

    @Query("SELECT * FROM dokter WHERE idDokter = :idDokter")
    fun getDokter(idDokter : String): Flow<Dokter>
}