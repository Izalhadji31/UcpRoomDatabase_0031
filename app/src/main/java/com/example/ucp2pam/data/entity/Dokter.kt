package com.example.ucp2pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dokter")
data class Dokter(
    @PrimaryKey
    val idDokter: String,
    val namaDokter: String,
    val spesialis: String,
    val klinik: String,
    val noHpDokter: String,
    val jamKerja: String,
)