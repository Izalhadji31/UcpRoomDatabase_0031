package com.example.ucp2pam.ui.viewmodel.jadwal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.repository.RepositoryJdwl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JadwalViewModel(
    private val repositoryJdwl: RepositoryJdwl
): ViewModel(){
    var uiState by mutableStateOf(JdwlUIState())

    val listDokter = repositoryJdwl.getAllNamaDokter()
        .onEach { dokterList ->
            Log.d("InsertViewModel", "Dokter List: $dokterList")
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // update state berdasarkan input
    fun updateState(jadwalEvent: JadwalEvent){
        uiState = uiState.copy(
            jadwalEvent = jadwalEvent,
        )
    }

    //validasi
    private fun validateFields(): Boolean{
        val event = uiState.jadwalEvent
        val errorState = FormErrorState(
            idJadwal = if (event.idJadwal.isNotEmpty()) null else "ID tidak boleh kosong!!",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong!!",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien boleh kosong!!",
            noHpPasien = if (event.noHpPasien.isNotEmpty()) null else "No Handphone Pasien tidak boleh kosong!!",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong!!",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong!!",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //save data
    fun saveData(){
        val currentEvent = uiState.jadwalEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryJdwl.insertJdwl(currentEvent.toJadwalEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        //reset input and error state
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorState(),
                    )
                }
                catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        }
        else{
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda."
            )
        }
    }

    //reset message snackbar
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// UI State
data class JdwlUIState(
    val jadwalEvent: JadwalEvent = JadwalEvent(),
    val isEntryValid: FormErrorState= FormErrorState(),
    val snackBarMessage: String? = null
)

// validation
data class FormErrorState(
    val idJadwal: String? = null,
    val namaDokter: String? = null,
    val namaPasien: String? = null,
    val noHpPasien: String? = null,
    val tanggalKonsultasi: String? = null,
    val status: String? = null,
){
    fun isValid(): Boolean{
        return idJadwal == null
                &&  namaDokter === null
                &&  namaPasien === null
                &&  noHpPasien === null
                &&  tanggalKonsultasi === null
                &&  status === null
    }
}

// store data form input
data class JadwalEvent(
    val idJadwal: String = "",
    val namaDokter: String = "",
    val namaPasien: String = "",
    val noHpPasien: String = "",
    val tanggalKonsultasi: String = "",
    val status: String = "",
)
fun JadwalEvent.toJadwalEntity():Jadwal = Jadwal(
    idJadwal = idJadwal,
    namaDokter = namaDokter,
    namaPasien = namaPasien,
    noHpPasien = noHpPasien,
    tanggalKonsultasi = tanggalKonsultasi,
    status = status
)