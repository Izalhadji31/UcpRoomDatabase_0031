package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDktr(private val dokterDao:DokterDao) : RepositoryDktr {
    override suspend fun insertDktr(dokter: Dokter) {
        dokterDao.insertDokter(dokter)
    }

    override fun getAllDktr(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    override fun getDktr(idDktr: String): Flow<Dokter> {
        return dokterDao.getDokter(idDktr)
    }

}
