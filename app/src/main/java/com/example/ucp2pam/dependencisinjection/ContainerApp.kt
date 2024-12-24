package com.example.ucp2pam.dependencisinjection

import android.content.Context
import com.example.ucp2pam.data.database.PrakdokDatabase
import com.example.ucp2pam.repository.LocalRepositoryDktr
import com.example.ucp2pam.repository.RepositoryDktr

import com.example.ucp2pam.repository.LocalRepositoryJdwl
import com.example.ucp2pam.repository.RepositoryJdwl

interface InterfaceContainerApp {
    val repositoryJdwl: RepositoryJdwl
    val repositoryDktr: RepositoryDktr
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repositoryDktr: RepositoryDktr by lazy {
        LocalRepositoryDktr(PrakdokDatabase.getDatabase(context).DokterDao())
    }
    override val repositoryJdwl: RepositoryJdwl by lazy {
        LocalRepositoryJdwl(PrakdokDatabase.getDatabase(context).JadwalDao())
    }
}