package com.example.a6_uaspam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a6_uaspam.AcaraApplications
import com.example.a6_uaspam.ui.viewmodel.acara.DetailAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.HomeAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.InsertAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.UpdateAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.DetailKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.HomeKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.InsertKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.UpdateKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.DetailLksViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.HomeLksViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.InsertLksViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.UpdateLksViewModel
import com.example.a6_uaspam.ui.viewmodel.vendor.DetailVndViewModel
import com.example.a6_uaspam.ui.viewmodel.vendor.HomeVndViewModel
import com.example.a6_uaspam.ui.viewmodel.vendor.InsertVndViewModel
import com.example.a6_uaspam.ui.viewmodel.vendor.UpdateVndViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Home View Model
        initializer { HomeAcrViewModel(aplikasiAcr().container.acaraRepository) }
        initializer { HomeLksViewModel(aplikasiAcr().container.lokasiRepository) }
        initializer { HomeKlnViewModel(aplikasiAcr().container.klienRepository) }
        initializer { HomeVndViewModel(aplikasiAcr().container.vendorRepository) }

        // Insert View Model
        initializer { InsertAcrViewModel(aplikasiAcr().container.acaraRepository, aplikasiAcr().container.klienRepository,aplikasiAcr().container.lokasiRepository) }
        initializer { InsertLksViewModel(aplikasiAcr().container.lokasiRepository) }
        initializer { InsertKlnViewModel(aplikasiAcr().container.klienRepository) }
        initializer { InsertVndViewModel(aplikasiAcr().container.vendorRepository) }

        // Detail View Model
        initializer { DetailAcrViewModel(createSavedStateHandle(),aplikasiAcr().container.acaraRepository, aplikasiAcr().container.klienRepository,aplikasiAcr().container.lokasiRepository) }
        initializer { DetailKlnViewModel(createSavedStateHandle(),aplikasiAcr().container.klienRepository) }
        initializer { DetailLksViewModel(createSavedStateHandle(),aplikasiAcr().container.lokasiRepository) }
        initializer { DetailVndViewModel(createSavedStateHandle(),aplikasiAcr().container.vendorRepository) }

        // Update View Model
        initializer { UpdateAcrViewModel(createSavedStateHandle(),aplikasiAcr().container.acaraRepository,aplikasiAcr().container.klienRepository,aplikasiAcr().container.lokasiRepository) }
        initializer { UpdateKlnViewModel(createSavedStateHandle(),aplikasiAcr().container.klienRepository) }
        initializer { UpdateLksViewModel(createSavedStateHandle(),aplikasiAcr().container.lokasiRepository) }
        initializer { UpdateVndViewModel(createSavedStateHandle(),aplikasiAcr().container.vendorRepository) }
    }
}

fun CreationExtras.aplikasiAcr(): AcaraApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AcaraApplications)