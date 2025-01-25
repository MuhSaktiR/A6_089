package com.example.a6_uaspam.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.Vendor
import com.example.a6_uaspam.repository.VendorRepository
import kotlinx.coroutines.launch

class InsertVndViewModel(private val vnd: VendorRepository) : ViewModel() {

    var uiStateVnd by mutableStateOf(InsertUiStateVnd())
        private set

    var formStateVnd: FormStateVnd by mutableStateOf(FormStateVnd.Idle)

    fun updateInsertVndState(insertUiEvent: InsertUiEventVnd) {
        uiStateVnd = InsertUiStateVnd(insertUiEvent = insertUiEvent)
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiStateVnd.insertUiEvent
        val errorState = FormErrorStateVnd(
            namaV = if (event.namaV.isNotEmpty()) null else "Nama Vendor tidak boleh kosong",
            jenisV = if (event.jenisV.isNotEmpty()) null else "Jenis Vendor tidak boleh kosong",
            telpV = if (event.telpV.isNotEmpty() || event.emailV.isNotEmpty()) null else "Minimal satu kontak harus diisi (Telepon atau Email)",
            emailV = if (event.telpV.isNotEmpty() || event.emailV.isNotEmpty()) null else "Minimal satu kontak harus diisi (Telepon atau Email)"
        )
        uiStateVnd = uiStateVnd.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


    suspend fun insertVnd() {
        if (validateFields()) {
            viewModelScope.launch {
                formStateVnd = FormStateVnd.Loading
                try {
                    vnd.insertVendor(uiStateVnd.insertUiEvent.toVnd())
                    formStateVnd = FormStateVnd.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    formStateVnd = FormStateVnd.Error("Data gagal disimpan")
                }
            }
        } else {
            formStateVnd = FormStateVnd.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiStateVnd = InsertUiStateVnd()
        formStateVnd = FormStateVnd.Idle
    }

    fun resetSnackBarMessage() {
        formStateVnd = FormStateVnd.Idle
    }
}

sealed class FormStateVnd {
    object Idle : FormStateVnd()
    object Loading : FormStateVnd()
    data class Success(val message: String) : FormStateVnd()
    data class Error(val message: String) : FormStateVnd()
}

data class InsertUiStateVnd(
    val insertUiEvent: InsertUiEventVnd = InsertUiEventVnd(),
    val isEntryValid: FormErrorStateVnd = FormErrorStateVnd()
)

data class FormErrorStateVnd(
    val namaV: String? = null,
    val jenisV: String? = null,
    val telpV: String? = null,
    val emailV: String? = null,
) {
    fun isValid(): Boolean {
        return namaV == null && jenisV == null && telpV == null && emailV == null
    }
}

data class InsertUiEventVnd(
    val idV: Int? = null,
    val namaV: String = "",
    val jenisV: String = "",
    val telpV: String = "",
    val emailV: String = ""
)

fun InsertUiEventVnd.toVnd(): Vendor = Vendor(
    idV = idV ?: 0,
    namaV = namaV,
    jenisV = jenisV,
    telpV = telpV ?: "",
    emailV = emailV ?: ""
)

fun Vendor.toUiStateVnd(): InsertUiStateVnd = InsertUiStateVnd(
    insertUiEvent = toInsertUiEvent()
)

fun Vendor.toInsertUiEvent(): InsertUiEventVnd = InsertUiEventVnd(
    idV = idV ?: 0,
    namaV = namaV,
    jenisV = jenisV,
    telpV = telpV ?: "",
    emailV = emailV ?: ""
)