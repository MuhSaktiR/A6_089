package com.example.a6_uaspam.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.repository.KlienRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiStateK {
    data class Success(val klien: List<Klien>) : HomeUiStateK()
    object Error : HomeUiStateK()
    object Loading : HomeUiStateK()
}

class HomeKlnViewModel(private val kln: KlienRepository) : ViewModel(){
    var klnUIState: HomeUiStateK by mutableStateOf(HomeUiStateK.Loading)
        private set

    init {
        getKln()
    }

    fun getKln(){
        viewModelScope.launch {
            klnUIState = HomeUiStateK.Loading
            klnUIState = try {
                HomeUiStateK.Success(kln.getKlien().data)
            } catch (e: IOException) {
                HomeUiStateK.Error
            } catch (e: HttpException){
                HomeUiStateK.Error
            }
        }
    }

    fun deleteKln(id: Int) {
        viewModelScope.launch {
            try {
                kln.deleteKlien(id)
            } catch (e: IOException) {
                HomeUiStateK.Error
            } catch (e: HttpException) {
                HomeUiStateK.Error
            }
        }
    }
}