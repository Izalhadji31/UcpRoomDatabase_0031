package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDktr {
        suspend fun insertDktr(dokter: Dokter)

        fun getAllDktr(): Flow<List<Dokter>>

        fun getDktr(idDktr: String): Flow<Dokter>
}