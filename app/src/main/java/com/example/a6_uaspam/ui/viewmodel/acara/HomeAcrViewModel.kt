package com.example.a6_uaspam.ui.viewmodel.acara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a6_uaspam.model.Acara
import com.example.a6_uaspam.repository.AcaraRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val acara: List<Acara>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeAcrViewModel(private val acr: AcaraRepository) : ViewModel(){
    var acrUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAcr()
    }

    fun getAcr(){
        viewModelScope.launch {
            acrUIState = HomeUiState.Loading
            acrUIState = try {
                HomeUiState.Success(acr.getAcara().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteAcr(id: Int) {
        viewModelScope.launch {
            try {
                acr.deleteAcara(id)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}