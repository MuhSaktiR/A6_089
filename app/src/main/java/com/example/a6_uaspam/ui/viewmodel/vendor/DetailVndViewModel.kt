package com.example.a6_uaspam.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a6_uaspam.model.VendorResponseDetail
import com.example.a6_uaspam.repository.VendorRepository
import com.example.a6_uaspam.ui.navigation.DestinasiDetailVnd
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiStateVnd {
    data class Success(val vnd: VendorResponseDetail) : DetailUiStateVnd()
    object Error : DetailUiStateVnd()
    object Loading : DetailUiStateVnd()
}

class DetailVndViewModel(
    savedStateHandle: SavedStateHandle,
    private val vnd: VendorRepository
) : ViewModel() {

    var detailUiStateVnd: DetailUiStateVnd by mutableStateOf(DetailUiStateVnd.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailVnd.ID])

    init {
        getVendorById()
    }

    fun getVendorById() {
        viewModelScope.launch {
            detailUiStateVnd = DetailUiStateVnd.Loading
            detailUiStateVnd = try {
                val vendor = vnd.getVendorById(_id)
                DetailUiStateVnd.Success(vendor)
            } catch (e: IOException) {
                DetailUiStateVnd.Error
            } catch (e: HttpException) {
                DetailUiStateVnd.Error
            }
        }
    }
}