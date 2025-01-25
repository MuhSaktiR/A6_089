package com.example.a6_uaspam.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.repository.VendorRepository
import com.example.a6_uaspam.ui.navigation.DestinasiUpdateVnd
import kotlinx.coroutines.launch

class UpdateVndViewModel(
    savedStateHandle: SavedStateHandle,
    private val vendorRepository: VendorRepository
): ViewModel(){
    var updateUiStateVnd by mutableStateOf(InsertUiStateVnd())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateVnd.ID])

    init {
        viewModelScope.launch {
            updateUiStateVnd = vendorRepository.getVendorById(_id).data
                .toUiStateVnd()
        }
    }

    fun updateInsertVndState(insertUiEvent: InsertUiEventVnd){
        updateUiStateVnd = InsertUiStateVnd(insertUiEvent = insertUiEvent)
    }

    suspend fun updateVnd(){
        viewModelScope.launch {
            try {
                vendorRepository.updateVendor(_id, updateUiStateVnd.insertUiEvent.toVnd())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}