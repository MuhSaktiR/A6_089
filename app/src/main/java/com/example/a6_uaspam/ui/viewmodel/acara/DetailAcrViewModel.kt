package com.example.a6_uaspam.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.AcaraResponseDetail
import com.example.a6_uaspam.repository.AcaraRepository
import com.example.a6_uaspam.ui.navigation.DestinasiDetailAcr
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiState {
    data class Success(val acr: AcaraResponseDetail) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailAcrViewModel(
    savedStateHandle: SavedStateHandle,
    private val acr: AcaraRepository
) : ViewModel() {

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailAcr.ID])

    init {
        getAcaraById()
    }

    fun getAcaraById() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val acara = acr.getAcaraById(_id)
                DetailUiState.Success(acara)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }
}