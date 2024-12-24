package com.example.ucp2pam.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.customwidget.DynamicSelectedField
import com.example.ucp2pam.ui.customwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.dokter.DktrUIState
import com.example.ucp2pam.ui.viewmodel.dokter.DokterEvent
import com.example.ucp2pam.ui.viewmodel.dokter.DokterViewModel
import com.example.ucp2pam.ui.viewmodel.dokter.FormErrorState
import kotlinx.coroutines.launch

@Composable
fun InsertDktrView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
// get uistate and snackbar
    val uiState = viewModel.uiState
    val snacBarHostState = remember{SnackbarHostState() }
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
                judul = "Tambah Dokter"
            )
            // isi body
            InsertBodyDktr (
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
fun InsertBodyDktr(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DktrUIState,
    onClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FormDokter(
            dokterEvent = uiState.dokterEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormDokter(
    modifier: Modifier = Modifier,
    onValueChange:(DokterEvent)-> Unit,
    dokterEvent: DokterEvent = DokterEvent(),
    errorState: FormErrorState = FormErrorState()
){
    var chosenDropdown by remember {
        mutableStateOf("")
    }

    //var listData: MutableList<String> = mutableListOf(chosenDropdown)
    val spesialis = listOf(
        "Dokter spesialis Jantung",
        "Dokter spesialis Anak",
        "Dokter spesialis Kulit",
        "Dokter spesialis Saraf"
    )

    Column (
        modifier = modifier.fillMaxWidth()
    ){
        // Id dokter
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.idDokter,
            onValueChange = {
                onValueChange(dokterEvent.copy(idDokter = it))
            },
            label = { Text("Id Dokter") },
            isError = errorState.idDokter != null,
            placeholder = { Text("Masukkan Id Dokter") }
        )
        Text(
            text = errorState.idDokter ?: "",
            color = Color.Red
        )
        // Name
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.namaDokter,
            onValueChange = {
                onValueChange(dokterEvent.copy(namaDokter = it))
            },
            label = { Text("Nama Dokter") },
            isError = errorState.namaDokter != null,
            placeholder = { Text("Masukkan Nama Dokter") }
        )
        Text(
            text = errorState.namaDokter ?: "",
            color = Color.Red
        )
        // Spesialis
        DynamicSelectedField(
            selectedValue = chosenDropdown,
            options = spesialis,
            lable = "Spesialis",
            onValueChangedEvent = { selectedSpesialisDokter ->
                onValueChange(dokterEvent.copy(spesialis = selectedSpesialisDokter))
                chosenDropdown = selectedSpesialisDokter
            }
        )
        Text(
            text = errorState.spesialis ?: "",
            color = Color.Red
        )

        // klinik
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.klinik,
            onValueChange = {
                onValueChange(dokterEvent.copy(klinik = it))
            },
            label = { Text("Klinik") },
            isError = errorState.klinik != null,
            placeholder = { Text("Masukkan Klinik") }
        )
        Text(
            text = errorState.klinik ?: "",
            color = Color.Red
        )

        // no HP
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.noHpDokter,
            onValueChange = {
                onValueChange(dokterEvent.copy(noHpDokter = it))
            },
            label = { Text("No Telepon Dokter") },
            isError = errorState.noHpDokter != null,
            placeholder = { Text("Masukkan No Telepon Dokter") }
        )
        Text(
            text = errorState.noHpDokter ?: "",
            color = Color.Red
        )

        // Jam kerja
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.jamKerja,
            onValueChange = {
                onValueChange(dokterEvent.copy(jamKerja = it))
            },
            label = { Text("Jam Kerja") },
            isError = errorState.jamKerja != null,
            placeholder = { Text("Masukkan Jam Kerja") }
        )
        Text(
            text = errorState.jamKerja ?: "",
            color = Color.Red
        )
    }
}