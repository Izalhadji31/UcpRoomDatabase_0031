package com.example.ucp2pam.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.repository.RepositoryDktr
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.launch

class DokterViewModel (
    private val repositoryDktr: RepositoryDktr
): ViewModel(){
    var uiState by mutableStateOf(DktrUIState())

    // update state berdasarkan input
    fun updateState(dokterEvent: DokterEvent){
        uiState = uiState.copy(
            dokterEvent = dokterEvent,
        )
    }

    private fun validateFields(): Boolean{
        val event = uiState.dokterEvent
        val errorState= FormErrorState(
            idDokter = if (event.idDokter.isNotEmpty()) null else "ID Dokter tidak boleh kosong!!",
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong!!",
            spesialis = if (event.spesialis.isNotEmpty()) null else "Specialis tidak boleh kosong!!",
            klinik = if (event.klinik.isNotEmpty()) null else "Klinik tidak boleh kosong!!",
            noHpDokter = if (event.noHpDokter.isNotEmpty()) null else "No HP Dokter. tidak boleh kosong!!",
            jamKerja = if (event.jamKerja.isNotEmpty()) null else "Jam Kerja tidak boleh kosong!!",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.dokterEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDktr.insertDktr(currentEvent.toDokterEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        //reset input and error state
                        dokterEvent = DokterEvent(),
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

// ui state
data class DktrUIState(
    val dokterEvent: DokterEvent = DokterEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

// validation
data class  FormErrorState(
    val idDokter: String? = null,
    val namaDokter: String? = null,
    val spesialis: String? = null,
    val klinik: String? = null,
    val noHpDokter: String? = null,
    val jamKerja: String? = null,
){
    fun isValid(): Boolean{
        return idDokter == null
                && namaDokter == null
                && spesialis == null
                && klinik == null
                && noHpDokter == null
                && jamKerja == null
    }
}

// tampung data
data class DokterEvent(
    val idDokter: String = "",
    val namaDokter: String = "",
    val spesialis: String = "",
    val klinik: String = "",
    val noHpDokter: String = "",
    val jamKerja: String = "",
)

fun DokterEvent.toDokterEntity():Dokter = Dokter(
    idDokter = idDokter,
    namaDokter = namaDokter,
    spesialis = spesialis,
    klinik = klinik,
    noHpDokter = noHpDokter,
    jamKerja = jamKerja
)