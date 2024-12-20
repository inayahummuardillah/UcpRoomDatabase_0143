package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.data.repository.RepositoryMatakuliah
import kotlinx.coroutines.launch

class MatakuliahViewModel(private val repositoryMatkul: RepositoryMatakuliah) : ViewModel() {

    var uiState by mutableStateOf(MatkulUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(matkulEvent: MatkulEvent) {
        uiState = uiState.copy(
            matkulEvent = matkulEvent,
        )
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiState.matkulEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS harus lebih dari 0",
            semester = if (event.semester.isNotEmpty()) null else "Semester harus lebih dari 0",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.matkulEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatkul.insertMatakuliah(currentEvent.toMatakuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        matkulEvent = MatkulEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak Valid. Periksa Kembali data yang Anda masukkan"
            )
        }
    }

    // Reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class MatkulUIState(
    val matkulEvent: MatkulEvent = MatkulEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null,
) {
    fun isValid(): Boolean {
        return kode == null && nama == null && sks == null && semester == null && jenis == null && dosenPengampu == null
    }
}

// Menyimpan input form ke dalam entity
fun MatkulEvent.toMatakuliahEntity(): Matakuliah = Matakuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

// Data class variable yang menyimpan data input form
data class MatkulEvent(
    val kode: String = "",
    val nama: String = "",
    val sks: String = "",
    val semester: String= "",
    val jenis: String = "",
    val dosenPengampu: String = "",
)
