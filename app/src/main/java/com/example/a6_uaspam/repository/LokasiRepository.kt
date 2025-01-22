package com.example.a6_uaspam.repository

import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.model.LokasiResponse
import com.example.a6_uaspam.model.LokasiResponseDetail
import com.example.a6_uaspam.service.LokasiService
import java.io.IOException

interface LokasiRepository {
    suspend fun getLokasi(): LokasiResponse
    suspend fun insertLokasi(lokasi: Lokasi)
    suspend fun updateLokasi(id: Int, lokasi: Lokasi)
    suspend fun deleteLokasi(id: Int)
    suspend fun getLokasiById(id: Int): LokasiResponseDetail
}

class NetworkLokasiRepository(
    private val lokasiApiService: LokasiService
) : LokasiRepository {

    override suspend fun getLokasi(): LokasiResponse {
        return lokasiApiService.getLokasi()
    }

    override suspend fun insertLokasi(lokasi: Lokasi) {
        lokasiApiService.insertLokasi(lokasi)
    }

    override suspend fun updateLokasi(id: Int, lokasi: Lokasi) {
        lokasiApiService.updateLokasi(id, lokasi)
    }

    override suspend fun deleteLokasi(id: Int) {
        try {
            val response = lokasiApiService.deleteLokasi(id)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Lokasi. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getLokasiById(id: Int): LokasiResponseDetail {
        return lokasiApiService.getLokasiById(id)
    }
}


