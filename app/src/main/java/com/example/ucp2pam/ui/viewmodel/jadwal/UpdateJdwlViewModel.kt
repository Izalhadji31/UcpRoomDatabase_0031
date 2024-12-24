package com.example.ucp2pam.ui.viewmodel.jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryJdwl
import com.example.ucp2pam.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import android.util.Log

class UpdateJdwlViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryJdwl: RepositoryJdwl
) : ViewModel(){
    var updateUIState by mutableStateOf(JdwlUIState())
        private set

    private val _idjadwal: String = checkNotNull(savedStateHandle[DestinasiUpdate.idjadwal])


    init {
        viewModelScope.launch {
            updateUIState = repositoryJdwl.getJdwl(_idjadwal)
                .filterNotNull()
                .first()
                .toUIStateJdwl()
        }
        Log.d("UpdateJadwalViewModel", "id jadwal init: $_idjadwal")
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        updateUIState = updateUIState.copy(
            jadwalEvent = jadwalEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.jadwalEvent
        val errorState = FormErrorState(
            idJadwal = if (event.idJadwal.isNotEmpty()) null else "ID tidak boleh kosong!!",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong!!",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien boleh kosong!!",
            noHpPasien = if (event.noHpPasien.isNotEmpty()) null else "No Handphone Pasien tidak boleh kosong!!",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong!!",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong!!",

            )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJdwl.updateJdwl(currentEvent.toJadwalEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.
                    snackBarMessage}")
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun Jadwal.toUIStateJdwl() : JdwlUIState = JdwlUIState(
    jadwalEvent = this.toDetailUiEvent(),
)