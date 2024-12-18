package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.entity.Dokter

class LocalRepositoryDktr (private val dokterDao: DokterDao) {

    // Fungsi untuk menambahkan Dokter (Create)
    suspend fun insertDokter(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    // Fungsi untuk mengambil semua Dokter (Read)
    suspend fun getAllDokter(): List<Dokter> {
        return dokterDao.getAllDokter()
    }

    // Fungsi untuk memperbarui data Dokter (Update)
    suspend fun updateDokter(dokter: Dokter) {
        dokterDao.updateDokter(dokter)
    }

    // Fungsi untuk menghapus Dokter (Delete)
    suspend fun deleteDokter(dokter: Dokter) {
        dokterDao.deleteDokter(dokter)
    }

    // Fungsi untuk mendapatkan detail Dokterberdasarkan ID
    suspend fun getDetailDokter(id: Int): Dokter {
        return dokterDao.getDetailDokter(id)
    }
}
