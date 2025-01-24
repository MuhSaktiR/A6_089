package com.example.a6_uaspam.ui.viewmodel.vendor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a6_uaspam.model.Vendor
import com.example.a6_uaspam.repository.VendorRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiStateV {
    data class Success(val vendor: List<Vendor>) : HomeUiStateV()
    object Error : HomeUiStateV()
    object Loading : HomeUiStateV()
}

class HomeVndViewModel(private val vnd: VendorRepository) : ViewModel(){
    var vndUIState: HomeUiStateV by mutableStateOf(HomeUiStateV.Loading)
        private set

    init {
        getVnd()
    }

    fun getVnd(){
        viewModelScope.launch {
            vndUIState = HomeUiStateV.Loading
            vndUIState = try {
                HomeUiStateV.Success(vnd.getVendor().data)
            } catch (e: IOException) {
                HomeUiStateV.Error
            } catch (e: HttpException){
                HomeUiStateV.Error
            }
        }
    }

    fun deleteVnd(id: Int) {
        viewModelScope.launch {
            try {
                vnd.deleteVendor(id)
            } catch (e: IOException) {
                HomeUiStateV.Error
            } catch (e: HttpException) {
                HomeUiStateV.Error
            }
        }
    }
}