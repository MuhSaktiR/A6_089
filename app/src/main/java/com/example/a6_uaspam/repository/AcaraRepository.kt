package com.example.a6_uaspam.repository

import android.util.Log
import com.example.a6_uaspam.model.Acara
import com.example.a6_uaspam.model.AcaraResponse
import com.example.a6_uaspam.model.AcaraResponseDetail
import com.example.a6_uaspam.service.AcaraService
import java.io.IOException

interface AcaraRepository {
    suspend fun getAcara(): AcaraResponse
    suspend fun insertAcara(acara: Acara)
    suspend fun updateAcara(id:Int, acara: Acara)
    suspend fun deleteAcara(id: Int)
    suspend fun getAcaraById(id: Int):AcaraResponseDetail
}

class NetworkAcaraRepository(
    private val acaraApiService: AcaraService
) : AcaraRepository{
    override suspend fun getAcara(): AcaraResponse {
        return try {
            Log.d("NetworkAcaraRepository", "Fetching acara data...")
            val response = acaraApiService.getAcara()
            Log.d("NetworkAcaraRepository", "Response received: $response")
            response
        } catch (e: Exception) {
            Log.e("NetworkAcaraRepository", "Error fetching data: ${e.message}")
            throw e
        }
    }

    override suspend fun insertAcara(acara: Acara) {
        try {
            Log.d("NetworkAcaraRepository", "Inserting acara: $acara")
            acaraApiService.insertAcara(acara)
            Log.d("NetworkAcaraRepository", "Acara successfully inserted")
        } catch (e: Exception) {
            Log.e("NetworkAcaraRepository", "Error inserting acara: ${e.message}")
            throw e
        }
    }

    override suspend fun updateAcara(id: Int, acara: Acara) {
        acaraApiService.updateAcara(id, acara)
    }

    override suspend fun deleteAcara(id: Int) {
        try {
            val response = acaraApiService.deleteAcara(id)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Acara. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getAcaraById(id: Int): AcaraResponseDetail {
        try {
            Log.d("AcaraRepository", "Fetching Acara with ID: $id")
            val response = acaraApiService.getAcaraById(id)
            if (response.status) {
                Log.d("AcaraRepository", "Successfully fetched Acara: ${response.data}")
            } else {
                Log.e("AcaraRepository", "Failed to fetch Acara: ${response.message}")
            }
            return response
        } catch (e: Exception) {
            Log.e("AcaraRepository", "Error during getAcaraById: ${e.message}", e)
            throw e
        }
    }
}