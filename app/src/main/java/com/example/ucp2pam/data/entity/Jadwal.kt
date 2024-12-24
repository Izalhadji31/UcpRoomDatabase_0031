package com.example.ucp2pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jadwal")
data class Jadwal(
    @PrimaryKey
    val idJadwal: String,
    val namaDokter: String,
    val namaPasien: String,
    val noHpPasien: String,
    val tanggalKonsultasi: String,
    val status: String,
)