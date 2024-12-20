package com.example.ucp2.ui.view.Matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.costumwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.Dosen.PenyediaDosenViewModel
import com.example.ucp2.ui.viewmodel.FormErrorState
import com.example.ucp2.ui.viewmodel.MatakuliahViewModel
import com.example.ucp2.ui.viewmodel.MatkulEvent
import com.example.ucp2.ui.viewmodel.MatkulUIState
import kotlinx.coroutines.launch


object DestinasiInsertMatakuliah : AlamatNavigasi {
    override val route: String = "insert_matkul"
} //object dikenal sebagai nama halaman di insertmatkulview

@Composable
fun InsertMatakuliahView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MatakuliahViewModel = viewModel(factory = PenyediaDosenViewModel.Factory)
) {
    val uiState = viewModel.uiState //ambil ui state dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() } //snackbar state
    val coroutineScope = rememberCoroutineScope()

    //observasi perubahan snackbarmessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) //tampilkan snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Matakuliah",
                modifier = modifier
            )
            //isi body
            InsertBodyMatkul(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent) //update state di viewmodel
                },
                onClick = {
                    viewModel.saveData() //simpan data
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatkul(
    modifier: Modifier = Modifier,
    onValueChange: (MatkulEvent) -> Unit,
    uiState: MatkulUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            matakuliahEvent = uiState.matkulEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMatakuliah(
    matakuliahEvent: MatkulEvent = MatkulEvent(),
    onValueChange: (MatkulEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisMatakuliah = listOf("Teori", "Praktikum")

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.kode,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(kode = it))
            },
            label = { Text("Kode") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukan kode matakuliah") },
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.nama,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukan nama matakuliah") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.sks,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukan jumlah SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.semester,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukan semester") },
        )
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Matakuliah")
        Row(modifier = Modifier.fillMaxWidth()) {
            jenisMatakuliah.forEach { jenis ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = matakuliahEvent.jenis == jenis,
                        onClick = {
                            onValueChange(matakuliahEvent.copy(jenis = jenis))
                        },
                    )
                    Text(
                        text = jenis,
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = matakuliahEvent.dosenPengampu,
            onValueChange = {
                onValueChange(matakuliahEvent.copy(dosenPengampu = it))
            },
            label = { Text("Dosen Pengampu") },
            isError = errorState.dosenPengampu != null,
            placeholder = { Text("Masukan nama dosen pengampu") },
        )
        Text(
            text = errorState.dosenPengampu ?: "",
            color = Color.Red
        )
    }
}
