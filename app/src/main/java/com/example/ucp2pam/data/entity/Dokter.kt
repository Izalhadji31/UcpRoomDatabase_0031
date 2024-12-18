package com.example.ucp2pam.data.entity

@Entity(tableName = "dokter")
data class Dokter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val spesialis: String,
    val klinik: String,
    val NoHp : String,
    val JamKerja: String
)
