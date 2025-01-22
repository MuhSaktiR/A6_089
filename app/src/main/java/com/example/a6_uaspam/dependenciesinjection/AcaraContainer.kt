package com.example.a6_uaspam.dependenciesinjection

import com.example.a6_uaspam.repository.AcaraRepository
import com.example.a6_uaspam.repository.KlienRepository
import com.example.a6_uaspam.repository.LokasiRepository
import com.example.a6_uaspam.repository.NetworkAcaraRepository
import com.example.a6_uaspam.repository.NetworkKlienRepository
import com.example.a6_uaspam.repository.NetworkLokasiRepository
import com.example.a6_uaspam.repository.NetworkVendorRepository
import com.example.a6_uaspam.repository.VendorRepository
import com.example.a6_uaspam.service.AcaraService
import com.example.a6_uaspam.service.KlienService
import com.example.a6_uaspam.service.LokasiService
import com.example.a6_uaspam.service.VendorService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val acaraRepository: AcaraRepository
    val lokasiRepository: LokasiRepository
    val vendorRepository: VendorRepository
    val klienRepository: KlienRepository
}

class AcaraContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/" //localhost diganti ip jika run di hp
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val acaraService: AcaraService by lazy {
        retrofit.create(AcaraService::class.java)
    }

    override val acaraRepository: AcaraRepository by lazy {
        NetworkAcaraRepository(acaraService)
    }

    private val lokasiService: LokasiService by lazy {
        retrofit.create(LokasiService::class.java)
    }

    override val lokasiRepository: LokasiRepository by lazy {
        NetworkLokasiRepository(lokasiService)
    }

    private val vendorService: VendorService by lazy {
        retrofit.create(VendorService::class.java)
    }

    override val vendorRepository: VendorRepository by lazy {
        NetworkVendorRepository(vendorService)
    }

    private val klienService: KlienService by lazy {
        retrofit.create(KlienService::class.java)
    }

    override val klienRepository: KlienRepository by lazy {
        NetworkKlienRepository(klienService)
    }
}