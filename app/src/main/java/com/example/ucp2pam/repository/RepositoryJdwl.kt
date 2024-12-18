package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.JadwalDao
import com.example.ucp2pam.data.entity.Jadwal

interface RepositoryJdwl {
    class JadwalRepository(private val dao: JadwalDao) {
        suspend fun insertJadwal(jadwal: Jadwal) = dao.insertJadwal(jadwal)
        suspend fun getAllJadwal() = dao.getAllJadwal()
    }

}