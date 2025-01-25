package com.example.a6_uaspam.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.repository.KlienRepository
import kotlinx.coroutines.launch

class InsertKlnViewModel(private val kln: KlienRepository) : ViewModel() {

    var uiStateKln by mutableStateOf(InsertUiStateKln())
        private set

    var formStateKln: FormStateKln by mutableStateOf(FormStateKln.Idle)

    fun updateInsertKlnState(insertUiEvent: InsertUiEventKln) {
        uiStateKln = InsertUiStateKln(insertUiEvent = insertUiEvent)
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiStateKln.insertUiEvent
        val errorState = FormErrorStateKln(
            namaK = if (event.namaK.isNotEmpty()) null else "Nama Klien tidak boleh kosong",
            telpK = if (event.telpK.isNotEmpty() || event.emailK.isNotEmpty()) null else "Minimal satu kontak harus diisi (Telepon atau Email)",
            emailK = if (event.telpK.isNotEmpty() || event.emailK.isNotEmpty()) null else "Minimal satu kontak harus diisi (Telepon atau Email)"
        )
        uiStateKln = uiStateKln.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


    suspend fun insertKln() {
        if (validateFields()) {
            viewModelScope.launch {
                formStateKln = FormStateKln.Loading
                try {
                    kln.insertKlien(uiStateKln.insertUiEvent.toKln())
                    formStateKln = FormStateKln.Success("Data berhasil disimpan")
                } catch (e: Exception) {
                    formStateKln = FormStateKln.Error("Data gagal disimpan")
                }
            }
        } else {
            formStateKln = FormStateKln.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiStateKln = InsertUiStateKln()
        formStateKln = FormStateKln.Idle
    }

    fun resetSnackBarMessage() {
        formStateKln = FormStateKln.Idle
    }
}

sealed class FormStateKln {
    object Idle : FormStateKln()
    object Loading : FormStateKln()
    data class Success(val message: String) : FormStateKln()
    data class Error(val message: String) : FormStateKln()
}

data class InsertUiStateKln(
    val insertUiEvent: InsertUiEventKln = InsertUiEventKln(),
    val isEntryValid: FormErrorStateKln = FormErrorStateKln()
)

data class FormErrorStateKln(
    val namaK: String? = null,
    val telpK: String? = null,
    val emailK: String? = null,
) {
    fun isValid(): Boolean {
        return namaK == null && telpK == null && emailK == null
    }
}

data class InsertUiEventKln(
    val idK: Int? = null,
    val namaK: String = "",
    val telpK: String = "",
    val emailK: String = ""
)

fun InsertUiEventKln.toKln(): Klien = Klien(
    idK = idK ?: 0,
    namaK = namaK,
    telpK = telpK ?: "",
    emailK = emailK ?: ""
)

fun Klien.toUiStateKln(): InsertUiStateKln = InsertUiStateKln(
    insertUiEvent = toInsertUiEvent()
)

fun Klien.toInsertUiEvent(): InsertUiEventKln = InsertUiEventKln(
    idK = idK ?: 0,
    namaK = namaK,
    telpK = telpK ?: "",
    emailK = emailK ?: ""
)