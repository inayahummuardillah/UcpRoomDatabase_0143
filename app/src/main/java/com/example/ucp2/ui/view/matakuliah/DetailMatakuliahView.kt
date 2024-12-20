package com.example.ucp2.ui.view.Matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.Dosen.PenyediaDosenViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.DetailMatakuliahViewModel
import com.example.ucp2.ui.viewmodel.Matakuliah.DetailUiState
import com.example.ucp2.ui.viewmodel.toMatakuliahEntity

@Composable
fun DetailMatakuliahView (
    modifier: Modifier = Modifier,
    viewModel: DetailMatakuliahViewModel = viewModel(factory = PenyediaDosenViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
){
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 18.dp),
        topBar = {
            TopAppBar (
                judul = "Detail Matakuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val kode = viewModel.detailUiState.value.detailUiEvent.kode
                    onEditClick(kode)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Matakuliah",
                )
            }
        }
    ) { innerPadding ->
        val detailMatkulUiState by viewModel.detailUiState.collectAsState()

        BodyDetailMatkul(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailMatkulUiState, // Pass the correct state here
            onDeleteClick = {
                viewModel.deleteMatkul()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailMatkul (
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState, // Receive the state here
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Tampilkan loading
            }
        }
        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMatkul(
                    matakuliah = detailUiState.detailUiEvent.toMatakuliahEntity(), // Use toMatakuliahEntity() properly
                    modifier = Modifier,
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
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


@Composable
fun ItemDetailMatkul(
    modifier: Modifier = Modifier,
    matakuliah: Matakuliah
){
    Card (
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors (
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column (
            modifier = Modifier.padding(16.dp)
        ){
            ComponentDetailMatkul(judul = "Kode", isinya = matakuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Nama", isinya = matakuliah.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "SKS", isinya = matakuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Semester", isinya = matakuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Jenis", isinya = matakuliah.jenis)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Dosen Pengampu", isinya = matakuliah.dosenPengampu)
        }
    }
}

@Composable
fun ComponentDetailMatkul(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray

        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Composable
private fun DeleteConfirmationDialog (
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier =
        Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
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
