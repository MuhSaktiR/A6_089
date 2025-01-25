package com.example.a6_uaspam.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.repository.AcaraRepository
import com.example.a6_uaspam.repository.KlienRepository
import com.example.a6_uaspam.repository.LokasiRepository
import com.example.a6_uaspam.ui.navigation.DestinasiUpdateAcr
import kotlinx.coroutines.launch

class UpdateAcrViewModel(
    savedStateHandle: SavedStateHandle,
    private val acaraRepository: AcaraRepository,
    private val kln: KlienRepository,
    private val lks: LokasiRepository
): ViewModel(){
    var updateUiState by mutableStateOf(InsertUiState())
        private set

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

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateAcr.ID])

    init {
        viewModelScope.launch {
            updateUiState = acaraRepository.getAcaraById(_id).data
                .toUiStateAcr()
        }
    }

    fun updateInsertAcrState(insertUiEvent: InsertUiEvent){
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateAcr(){
        viewModelScope.launch {
            try {
                acaraRepository.updateAcara(_id, updateUiState.insertUiEvent.toAcr())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}