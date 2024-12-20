package com.example.ucp2.ui.viewmodel.Matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Matakuliah
import com.example.ucp2.data.repository.RepositoryMatakuliah
import com.example.ucp2.ui.navigation.DestinasiDetailMatakuliah
import com.example.ucp2.ui.viewmodel.MatkulEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class DetailMatakuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatakuliah: RepositoryMatakuliah,
) : ViewModel() {
    fun deleteMatkul() {
        TODO("Not yet implemented")
    }

    private val kode: String = checkNotNull(savedStateHandle[DestinasiDetailMatakuliah.KODE])

    val detailUiState: StateFlow<DetailUiState> = repositoryMatakuliah.getMatakuliah(kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailMatkulEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )
}

data class DetailUiState(
    val detailUiEvent: MatkulEvent = MatkulEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MatkulEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MatkulEvent()
}

/* Data class untuk menampung data yang akan ditampilkan di UI */
// Memindahkan data dari entity ke UI
fun Matakuliah.toDetailMatkulEvent(): MatkulEvent {
    return MatkulEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}

//data class MatakuliahEvent(
//    val kode: String = "",
//    val nama: String = "",
//    val sks: Int = 0,
//    val semester: Int = 0,
//    val jenis: String = "",
//    val dosenPengampu: String = ""
//)
