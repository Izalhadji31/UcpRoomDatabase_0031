package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Jadwal

class LocalRepositoryJdwl (private val jadwalDao: JadwalDao){


    // Fungsi untuk menambahkan Jadwal (Create)
    suspend fun insertJadwal(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal)
    }

    // Fungsi untuk mengambil semua data Jadwal (Read)
    suspend fun getAllJadwal(): List<Jadwal> {
        return jadwalDao.getAllJadwal()
    }
}