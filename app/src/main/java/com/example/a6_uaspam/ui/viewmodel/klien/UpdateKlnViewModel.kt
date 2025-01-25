package com.example.a6_uaspam.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.repository.KlienRepository
import com.example.a6_uaspam.ui.navigation.DestinasiUpdateKln
import kotlinx.coroutines.launch

class UpdateKlnViewModel(
    savedStateHandle: SavedStateHandle,
    private val klienRepository: KlienRepository
): ViewModel(){
    var updateUiStateKln by mutableStateOf(InsertUiStateKln())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateKln.ID])

    init {
        viewModelScope.launch {
            updateUiStateKln = klienRepository.getKlienById(_id).data
                .toUiStateKln()
        }
    }

    fun updateInsertKlnState(insertUiEvent: InsertUiEventKln){
        updateUiStateKln = InsertUiStateKln(insertUiEvent = insertUiEvent)
    }

    suspend fun updateKln(){
        viewModelScope.launch {
            try {
                klienRepository.updateKlien(_id, updateUiStateKln.insertUiEvent.toKln())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}