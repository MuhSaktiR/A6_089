package com.example.a6_uaspam.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.repository.LokasiRepository
import com.example.a6_uaspam.ui.navigation.DestinasiUpdateLks
import kotlinx.coroutines.launch

class UpdateLksViewModel(
    savedStateHandle: SavedStateHandle,
    private val lokasiRepository: LokasiRepository
): ViewModel(){
    var updateUiStateLks by mutableStateOf(InsertUiStateLks())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateLks.ID])

    init {
        viewModelScope.launch {
            updateUiStateLks = lokasiRepository.getLokasiById(_id).data
                .toUiStateLks()
        }
    }

    fun updateInsertLksState(insertUiEvent: InsertUiEventLks){
        updateUiStateLks = InsertUiStateLks(insertUiEvent = insertUiEvent)
    }

    suspend fun updateLks(){
        viewModelScope.launch {
            try {
                lokasiRepository.updateLokasi(_id, updateUiStateLks.insertUiEvent.toLks())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}