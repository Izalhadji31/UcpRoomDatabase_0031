    package com.example.ucp2pam.ui.view.jadwal

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Edit
    import androidx.compose.material3.AlertDialog
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.FloatingActionButton
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.material3.TextField
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.colorResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.ucp2pam.R
    import com.example.ucp2pam.data.entity.Jadwal
    import com.example.ucp2pam.ui.customwidget.TopAppBar
    import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
    import com.example.ucp2pam.ui.viewmodel.jadwal.DetailJdwlViewModel
    import com.example.ucp2pam.ui.viewmodel.jadwal.DetailUiState
    import com.example.ucp2pam.ui.viewmodel.jadwal.toJadwalEntity

    @Composable
    fun DetailJdwlView(
        modifier: Modifier = Modifier,
        viewModel: DetailJdwlViewModel = viewModel(factory = PenyediaViewModel.Factory),
        onBack: () -> Unit = {},
        onEditClick: () -> Unit = {},
        onDeleteClick: () -> Unit = {}
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    judul = "Detail Jadwal Pasien",
                    showBackButton = true,
                    onBack = onBack
                )
            }
        ) { innerPadding ->
            val detailUiState by viewModel.detailUiState.collectAsState()

            BodyDetailJdwl(
                modifier = Modifier.padding(innerPadding),
                detailUiState = detailUiState,
                onDeleteClick = {
                    viewModel.deteleJdwl()
                    onDeleteClick()
                }
            )
        }
    }


    @Composable
    fun BodyDetailJdwl(
        modifier: Modifier = Modifier,
        detailUiState: DetailUiState = DetailUiState(),
        onSaveClick: (Jadwal) -> Unit = {},
        onDeleteClick: () -> Unit = {}
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        var isEditing by rememberSaveable { mutableStateOf(false) }

        when {
            detailUiState.isLoading -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            detailUiState.isUiEventNotEmpty -> {
                val jadwal = detailUiState.detailUiEvent.toJadwalEntity()

                Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
                    if (isEditing) {
                        // Form Edit
                        var namaDokter by rememberSaveable { mutableStateOf(jadwal.namaDokter) }
                        var namaPasien by rememberSaveable { mutableStateOf(jadwal.namaPasien) }
                        var noHpPasien by rememberSaveable { mutableStateOf(jadwal.noHpPasien) }
                        var tanggalKonsultasi by rememberSaveable { mutableStateOf(jadwal.tanggalKonsultasi) }
                        var statusPasien by rememberSaveable { mutableStateOf(jadwal.status) }

                        TextField(
                            value = namaDokter,
                            onValueChange = { namaDokter = it },
                            label = { Text("Nama Dokter") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = namaPasien,
                            onValueChange = { namaPasien = it },
                            label = { Text("Nama Pasien") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = noHpPasien,
                            onValueChange = { noHpPasien = it },
                            label = { Text("No Telpon") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = tanggalKonsultasi,
                            onValueChange = { tanggalKonsultasi = it },
                            label = { Text("Tanggal Konsultasi") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = statusPasien,
                            onValueChange = { statusPasien = it },
                            label = { Text("Status Pasien") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    // Simpan perubahan
                                    val updatedJadwal = jadwal.copy(
                                        namaDokter = namaDokter,
                                        namaPasien = namaPasien,
                                        noHpPasien = noHpPasien,
                                        tanggalKonsultasi = tanggalKonsultasi,
                                        status = statusPasien
                                    )
                                    onSaveClick(updatedJadwal)
                                    isEditing = false
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Save")
                            }
                            Button(
                                onClick = { isEditing = false },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Gray,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Cancel")
                            }
                        }
                    } else {
                        // Tampilan Detail
                        ItemDetailJdwl(
                            jadwal = jadwal,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Button(
                            onClick = { deleteConfirmationRequired = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.purple_200),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Delete", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { isEditing = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.teal_200),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Edit", fontSize = 18.sp)
                        }

                        if (deleteConfirmationRequired) {
                            DeleteConfirmationDialog(
                                onDeleteConfirm = {
                                    deleteConfirmationRequired = false
                                    onDeleteClick()
                                },
                                onDeleteCancel = {
                                    deleteConfirmationRequired = false
                                }, modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }

            detailUiState.isUiEventEmpty -> {
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Data tidak ditemukan",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }



    // item detail
    @Composable
    fun ItemDetailJdwl(
        modifier: Modifier = Modifier,
        jadwal: Jadwal
    ){
        Card(
            modifier = modifier.fillMaxWidth().padding(top = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)
            ) {
                ComponentDetailJdwl(judul = "Id Jadwal", isinya = jadwal.idJadwal)
                Spacer(modifier =Modifier.padding(5.dp))
                ComponentDetailJdwl(judul = "Nama Dokter", isinya = jadwal.namaDokter)
                Spacer(modifier =Modifier.padding(5.dp))
                ComponentDetailJdwl(judul = "Nama Pasien", isinya = jadwal.namaPasien)
                Spacer(modifier =Modifier.padding(5.dp))
                ComponentDetailJdwl(judul = "No Telpon", isinya = jadwal.noHpPasien)
                Spacer(modifier =Modifier.padding(5.dp))
                ComponentDetailJdwl(judul = "Taggal Konsultasi", isinya = jadwal.tanggalKonsultasi)
                Spacer(modifier =Modifier.padding(5.dp))
                ComponentDetailJdwl(judul = "Status Pasien", isinya = jadwal.status)

            }
        }
    }

    // component detail
    @Composable
    fun ComponentDetailJdwl(
        modifier: Modifier =Modifier,
        judul: String,
        isinya: String,
    ){
        Column(modifier =modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start)
        {
            Text(
                text = "$judul : ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = isinya,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    // delete JADWAL
    @Composable
    private fun DeleteConfirmationDialog(
        onDeleteConfirm: () -> Unit,
        onDeleteCancel: () -> Unit,
        modifier: Modifier = Modifier
    ){
        AlertDialog(onDismissRequest = {},
            title = { Text("Delete Data") },
            text = { Text("Apakah anda yakin ingin menghapus data?") },
            modifier = modifier,
            dismissButton = {
                TextButton(onClick = onDeleteCancel) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                TextButton(onClick = onDeleteConfirm) {
                    Text(text = "Yes")
                }
            }
        )
    }
