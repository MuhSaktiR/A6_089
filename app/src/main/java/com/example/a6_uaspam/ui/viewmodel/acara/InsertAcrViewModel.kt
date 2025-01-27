package com.example.a6_uaspam.ui.viewmodel.acara

import com.example.a6_uaspam.model.Acara
import com.example.a6_uaspam.repository.AcaraRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.repository.KlienRepository
import com.example.a6_uaspam.repository.LokasiRepository
import kotlinx.coroutines.launch

class InsertAcrViewModel(
    private val acr: AcaraRepository,
    private val kln: KlienRepository,
    private val lks: LokasiRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    var formState: FormState by mutableStateOf(FormState.Idle)

    // Menyimpan data klien dan lokasi yang dimuat
    var klienList by mutableStateOf<List<Klien>>(emptyList())
    var lokasiList by mutableStateOf<List<Lokasi>>(emptyList())

    init {
        // Memuat data klien dan lokasi
        loadKlienAndLokasi()
    }

    private fun loadKlienAndLokasi() {
        viewModelScope.launch {
            try {
                klienList = kln.getKlien().data
                lokasiList = lks.getLokasi().data
            } catch (e: Exception) {
                // Tangani kesalahan jika ada
            }
        }
    }

    fun updateInsertAcrState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiState.insertUiEvent
        val errorState = FormErrorState(
            nama = if (event.nama.isNotEmpty()) null else "Nama Acara tidak boleh kosong",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi Acara tidak boleh kosong",
            tanggalMulai = if (event.tanggalMulai.isNotEmpty()) null else "Tanggal Mulai Acara tidak boleh kosong",
            tanggalBerakhir = if (event.tanggalBerakhir.isNotEmpty()) null else "Tanggal Berakhir Acara tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status Acara tidak boleh kosong",
            idK = if (event.idK != null) null else "Pilihan Klien tidak boleh kosong",
            idL = if (event.idL != null) null else "Pilihan Lokasi tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertAcr() {
        if (validateFields()) {
            viewModelScope.launch {
                formState = FormState.Loading
                try {
                    acr.insertAcara(uiState.insertUiEvent.toAcr())
                    formState = FormState.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    formState = FormState.Error("Data gagal disimpan")
                }
            }
        } else {
            formState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiState = InsertUiState()
        formState = FormState.Idle
    }

    fun resetSnackBarMessage() {
        formState = FormState.Idle
    }
}

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

data class FormErrorState(
    val nama: String? = null,
    val deskripsi: String? = null,
    val tanggalMulai: String? = null,
    val tanggalBerakhir: String? = null,
    val status: String? = null,
    val idK: String? = null,
    val idL: String? = null
) {
    fun isValid(): Boolean {
        return nama == null && deskripsi == null && tanggalMulai == null &&
                tanggalBerakhir == null && status == null && idK == null && idL == null
    }
}

data class InsertUiEvent(
    val id: Int? = null,
    val nama: String = "",
    val deskripsi: String = "",
    val tanggalMulai: String = "",
    val tanggalBerakhir: String = "",
    val status: String = "",
    val idK: Int? = null,
    val idL: Int? = null
)

fun InsertUiEvent.toAcr(): Acara = Acara(
    id = id ?: 0,
    nama = nama,
    deskripsi = deskripsi,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    status = status,
    idK = idK ?: 0,
    idL = idL ?: 0,
)

fun Acara.toUiStateAcr(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Acara.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    id = id ?: 0,
    nama = nama,
    deskripsi = deskripsi,
    tanggalMulai = tanggalMulai,
    tanggalBerakhir = tanggalBerakhir,
    status = status,
    idK = idK ?: 0,
    idL = idL ?: 0
)
