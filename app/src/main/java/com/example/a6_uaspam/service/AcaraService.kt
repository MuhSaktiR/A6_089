package com.example.a6_uaspam.service

import com.example.a6_uaspam.model.Acara
import com.example.a6_uaspam.model.AcaraResponse
import com.example.a6_uaspam.model.AcaraResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AcaraService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("api/acara")
    suspend fun getAcara() : AcaraResponse

    @GET("api/acara/{id_acara}")
    suspend fun  getAcaraById(@Path("id_acara") id:Int): AcaraResponseDetail

    @POST("api/acara/store")
    suspend fun insertAcara(@Body acara: Acara)

    @PUT("api/acara/{id_acara}")
    suspend fun updateAcara(@Path("id_acara") id: Int, @Body acara: Acara)

    @DELETE("api/acara/{id_acara}")
    suspend fun deleteAcara(@Path("id_acara") id: Int) : Response<Void>
}