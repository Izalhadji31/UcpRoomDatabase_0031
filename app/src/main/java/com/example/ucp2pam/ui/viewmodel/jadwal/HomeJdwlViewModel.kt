package com.example.ucp2pam.ui.viewmodel.jadwal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryJdwl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeJdwlViewModel(
    private val repositoryJdwl: RepositoryJdwl
): ViewModel() {
    val HomeUiStateJadwal: StateFlow<HomeUiStateJadwal> = repositoryJdwl.getAllJdwl()
        .filterNotNull()
        .map {
            HomeUiStateJadwal(
                listJdwl = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateJadwal(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateJadwal(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateJadwal(
                isLoading = true,
            )
        )
}

// state update ui
data class HomeUiStateJadwal(
    val listJdwl: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)