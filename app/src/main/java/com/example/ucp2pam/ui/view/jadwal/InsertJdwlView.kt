package com.example.ucp2pam.ui.view.jadwal

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.ui.customwidget.DynamicSelectedField
import com.example.ucp2pam.ui.customwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalEvent
import com.example.ucp2pam.ui.viewmodel.jadwal.FormErrorState
import com.example.ucp2pam.ui.viewmodel.jadwal.JadwalViewModel
import com.example.ucp2pam.ui.viewmodel.jadwal.JdwlUIState
import kotlinx.coroutines.launch

@Composable
fun InsertJdwlView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // get uistate and snackbar
    val uiState = viewModel.uiState
    val snacBarHostState = remember{ SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // observe on change snack bar
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                //tampil snackbar
                snacBarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snacBarHostState) }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Jadwal"
            )

            //isi body
            InserBodyJdwl(
                uiState = uiState,
                onValueChange = { uppdateEvent ->
                    viewModel.updateState(uppdateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InserBodyJdwl(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JdwlUIState,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple_200),
                contentColor = Color.White
            )
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormJadwal(
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(),
    onValueChange:(JadwalEvent)-> Unit,
    jadwalEvent: JadwalEvent = JadwalEvent(),
    errorState: FormErrorState = FormErrorState(),
){
    var chosenDropdownStatus by remember {
        mutableStateOf("")
    }
    var chosenDropdownNamaDokter by remember {
        mutableStateOf("")
    }

    var listNamaDokter by remember { mutableStateOf<List<String>>(emptyList()) }

    val statusPasien = listOf(
        "Terjadwal (Scheduled)",
        "Tunggu (Waiting)",
        "Sedang Diperiksa (In Progress)",
        "Selesai (Completed)",
        "Batal (Canceled)",
        "Tunda (Rescheduled)",
        "Menunggu Hasil (Waiting for Results)",
    )

    Column (
        modifier = modifier.fillMaxWidth()
    ){
        // ID Jadwal
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.idJadwal,
            onValueChange = {
                onValueChange(jadwalEvent.copy(idJadwal = it))
            },
            label = { Text("ID Jadwal") },
            isError = errorState.idJadwal != null,
            placeholder = { Text("Masukkan Id jadwal") }
        )
        Text(
            text = errorState.idJadwal ?: "",
            color = Color.Red
        )
        // Nama Dokter
        LaunchedEffect(Unit) {
            viewModel.listDokter.collect { dokterList ->
                listNamaDokter = dokterList.map { it.namaDokter }
            }
        }
        DynamicSelectedField(
            selectedValue = chosenDropdownNamaDokter,
            options = listNamaDokter,
            lable = "Nama Dokter",
            onValueChangedEvent = { selectedNamaDokter ->
                val updatedEvent = jadwalEvent.copy(namaDokter = selectedNamaDokter)
                onValueChange(updatedEvent)
                chosenDropdownNamaDokter = selectedNamaDokter
            }

        )
        Text(
            text = errorState.namaDokter ?: "",
            color = Color.Red
        )

        // Nama Pasien
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien = it))
            },
            label = { Text("Nama Pasien") },
            isError = errorState.namaPasien != null,
            placeholder = { Text("Masukkan Nama Pasien") }
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )

        // no HP
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.noHpPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(noHpPasien = it))
            },
            label = { Text("No Telepon") },
            isError = errorState.noHpPasien != null,
            placeholder = { Text("Masukkan No Telepon") }
        )
        Text(
            text = errorState.noHpPasien ?: "",
            color = Color.Red
        )
        // tanggal Konsultasi
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tanggalKonsultasi,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tanggalKonsultasi = it))
            },
            label = { Text("Tanggal Konsultasi") },
            isError = errorState.tanggalKonsultasi != null,
            placeholder = { Text("Masukkan Tanggal konsultasi") }
        )
        Text(
            text = errorState.tanggalKonsultasi ?: "",
            color = Color.Red
        )
        // Status
        DynamicSelectedField(
            selectedValue = chosenDropdownStatus,
            options = statusPasien,
            lable = "Status Pasien",
            onValueChangedEvent = { selectedStatusPasien ->
                onValueChange(jadwalEvent.copy(status = selectedStatusPasien))
                chosenDropdownStatus = selectedStatusPasien
            }
        )
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )
    }
}