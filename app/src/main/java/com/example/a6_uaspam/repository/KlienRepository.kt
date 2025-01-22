package com.example.a6_uaspam.repository

import android.util.Log
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.model.KlienResponse
import com.example.a6_uaspam.model.KlienResponseDetail
import com.example.a6_uaspam.service.KlienService
import java.io.IOException

interface KlienRepository {
    suspend fun getKlien(): KlienResponse
    suspend fun insertKlien(klien: Klien)
    suspend fun updateKlien(id: Int, klien: Klien)
    suspend fun deleteKlien(id: Int)
    suspend fun getKlienById(id: Int): KlienResponseDetail
}

class NetworkKlienRepository(
    private val klienApiService: KlienService
) : KlienRepository {

    override suspend fun getKlien(): KlienResponse {
        return klienApiService.getKlien()
    }

    override suspend fun insertKlien(klien: Klien) {
        klienApiService.insertKlien(klien)
    }

    override suspend fun updateKlien(id: Int, klien: Klien) {
        klienApiService.updateKlien(id, klien)
    }

    override suspend fun deleteKlien(id: Int) {
        try {
            val response = klienApiService.deleteKlien(id)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Klien. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getKlienById(id: Int): KlienResponseDetail {
        return klienApiService.getKlienById(id)
    }
}



