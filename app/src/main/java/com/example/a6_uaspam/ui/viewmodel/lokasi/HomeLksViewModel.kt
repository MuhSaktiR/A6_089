package com.example.a6_uaspam.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.repository.LokasiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiStateL {
    data class Success(val lokasi: List<Lokasi>) : HomeUiStateL()
    object Error : HomeUiStateL()
    object Loading : HomeUiStateL()
}

class HomeLksViewModel(private val lks: LokasiRepository) : ViewModel(){
    var lksUIState: HomeUiStateL by mutableStateOf(HomeUiStateL.Loading)
        private set

    init {
        getLks()
    }

    fun getLks(){
        viewModelScope.launch {
            lksUIState = HomeUiStateL.Loading
            lksUIState = try {
                HomeUiStateL.Success(lks.getLokasi().data)
            } catch (e: IOException) {
                HomeUiStateL.Error
            } catch (e: HttpException){
                HomeUiStateL.Error
            }
        }
    }

    fun deleteLks(id: Int) {
        viewModelScope.launch {
            try {
                lks.deleteLokasi(id)
            } catch (e: IOException) {
                HomeUiStateL.Error
            } catch (e: HttpException) {
                HomeUiStateL.Error
            }
        }
    }
}