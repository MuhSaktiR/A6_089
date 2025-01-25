package com.example.a6_uaspam.ui.viewmodel.klien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.KlienResponseDetail
import com.example.a6_uaspam.repository.KlienRepository
import com.example.a6_uaspam.ui.navigation.DestinasiDetailAcr
import com.example.a6_uaspam.ui.navigation.DestinasiDetailKln
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiStateKln {
    data class Success(val kln: KlienResponseDetail) : DetailUiStateKln()
    object Error : DetailUiStateKln()
    object Loading : DetailUiStateKln()
}

class DetailKlnViewModel(
    savedStateHandle: SavedStateHandle,
    private val kln: KlienRepository
) : ViewModel() {

    var detailUiStateKln: DetailUiStateKln by mutableStateOf(DetailUiStateKln.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailKln.ID])

    init {
        getKlienById()
    }

    fun getKlienById() {
        viewModelScope.launch {
            detailUiStateKln = DetailUiStateKln.Loading
            detailUiStateKln = try {
                val klien = kln.getKlienById(_id)
                DetailUiStateKln.Success(klien)
            } catch (e: IOException) {
                DetailUiStateKln.Error
            } catch (e: HttpException) {
                DetailUiStateKln.Error
            }
        }
    }
}