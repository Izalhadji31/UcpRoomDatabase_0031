package com.example.ucp2pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.PrakdokApp
import com.example.ucp2pam.ui.viewmodel.dokter.DokterViewModel
import com.example.ucp2pam.ui.viewmodel.dokter.HomeDokterViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.DetailJdwlViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.HomeJdwlViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.UpdateJdwlViewModel


object PenyediaViewModel{
    val Factory = viewModelFactory {
        // view model Dokter
        initializer {
            DokterViewModel(
                PrakdokApp().containerApp.repositoryDktr
            )
        }
        initializer {
            HomeDokterViewModel(
                PrakdokApp().containerApp.repositoryDktr
            )
        }
        // Jadwal View Model
        initializer {
            JadwalViewModel(
                PrakdokApp().containerApp.repositoryJdwl
            )
        }
        initializer {
            HomeJdwlViewModel(
                PrakdokApp().containerApp.repositoryJdwl
            )
        }
        initializer {
            DetailJdwlViewModel(
                createSavedStateHandle(),
                PrakdokApp().containerApp.repositoryJdwl
            )
        }
        initializer {
            UpdateJdwlViewModel(
                createSavedStateHandle(),
                PrakdokApp().containerApp.repositoryJdwl
            )
        }

    }
}

fun CreationExtras.PrakdokApp(): PrakdokApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PrakdokApp)