package com.example.a6_uaspam.ui.viewmodel.lokasi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.LokasiResponseDetail
import com.example.a6_uaspam.repository.LokasiRepository
import com.example.a6_uaspam.ui.navigation.DestinasiDetailLks
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiStateLks {
    data class Success(val lks: LokasiResponseDetail) : DetailUiStateLks()
    object Error : DetailUiStateLks()
    object Loading : DetailUiStateLks()
}

class DetailLksViewModel(
    savedStateHandle: SavedStateHandle,
    private val lks: LokasiRepository
) : ViewModel() {

    var detailUiStateLks: DetailUiStateLks by mutableStateOf(DetailUiStateLks.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailLks.ID])

    init {
        getLokasiById()
    }

    fun getLokasiById() {
        viewModelScope.launch {
            detailUiStateLks = DetailUiStateLks.Loading
            detailUiStateLks = try {
                val lokasi = lks.getLokasiById(_id)
                DetailUiStateLks.Success(lokasi)
            } catch (e: IOException) {
                DetailUiStateLks.Error
            } catch (e: HttpException) {
                DetailUiStateLks.Error
            }
        }
    }
}