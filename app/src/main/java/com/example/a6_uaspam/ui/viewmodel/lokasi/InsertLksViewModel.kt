package com.example.a6_uaspam.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.repository.LokasiRepository
import kotlinx.coroutines.launch

class InsertLksViewModel(private val lks: LokasiRepository) : ViewModel() {

    var uiStateLks by mutableStateOf(InsertUiStateLks())
        private set

    var formStateLks: FormStateLks by mutableStateOf(FormStateLks.Idle)

    fun updateInsertLksState(insertUiEvent: InsertUiEventLks) {
        uiStateLks = InsertUiStateLks(insertUiEvent = insertUiEvent)
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiStateLks.insertUiEvent
        val errorState = FormErrorStateLks(
            namaL = if (event.namaL.isNotEmpty()) null else "Nama Lokasi tidak boleh kosong",
            alamatL = if (event.alamatL.isNotEmpty()) null else "Alamat Lokasi tidak boleh kosong",
            kapasitas = if (event.kapasitas != null && event.kapasitas > 0) null else "Kapasitas Lokasi harus diisi dan lebih besar dari 0"
        )
        uiStateLks = uiStateLks.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertLks() {
        if (validateFields()) {
            viewModelScope.launch {
                formStateLks = FormStateLks.Loading
                try {
                    lks.insertLokasi(uiStateLks.insertUiEvent.toLks())
                    formStateLks = FormStateLks.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    formStateLks = FormStateLks.Error("Data gagal disimpan")
                }
            }
        } else {
            formStateLks = FormStateLks.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiStateLks = InsertUiStateLks()
        formStateLks = FormStateLks.Idle
    }

    fun resetSnackBarMessage() {
        formStateLks = FormStateLks.Idle
    }
}

sealed class FormStateLks {
    object Idle : FormStateLks()
    object Loading : FormStateLks()
    data class Success(val message: String) : FormStateLks()
    data class Error(val message: String) : FormStateLks()
}

data class InsertUiStateLks(
    val insertUiEvent: InsertUiEventLks = InsertUiEventLks(),
    val isEntryValid: FormErrorStateLks = FormErrorStateLks()
)

data class FormErrorStateLks(
    val namaL: String? = null,
    val alamatL: String? = null,
    val kapasitas: String? = null
) {
    fun isValid(): Boolean {
        return namaL == null && alamatL == null && kapasitas == null
    }
}

data class InsertUiEventLks(
    val idL: Int? = null,
    val namaL: String = "",
    val alamatL: String = "",
    val kapasitas: Int? = null,
)

fun InsertUiEventLks.toLks(): Lokasi = Lokasi(
    idL = idL ?: 0,
    namaL = namaL,
    alamatL = alamatL,
    kapasitas = kapasitas ?: 0
)

fun Lokasi.toUiStateLks(): InsertUiStateLks = InsertUiStateLks(
    insertUiEvent = toInsertUiEvent()
)

fun Lokasi.toInsertUiEvent(): InsertUiEventLks = InsertUiEventLks(
    idL = idL ?: 0,
    namaL = namaL,
    alamatL = alamatL,
    kapasitas = kapasitas
)