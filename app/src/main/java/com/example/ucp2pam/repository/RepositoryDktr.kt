package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.entity.Dokter

interface RepositoryDktr {
        class DokterRepository(private val dao: DokterDao) {
                suspend fun insertDokter(dokter: Dokter) = dao.insertDokter(dokter)
                suspend fun getAllDokter() = dao.getAllDokter()
                suspend fun updateDokter(dokter: Dokter) = dao.updateDokter(dokter)
                suspend fun deleteDokter(dokter: Dokter) = dao.deleteDokter(dokter)
                suspend fun getDetailDokter(idDokter: Int) = dao.getDetailDokter(idDokter)
        }
}