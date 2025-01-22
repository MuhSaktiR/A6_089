package com.example.a6_uaspam.service

import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.model.KlienResponse
import com.example.a6_uaspam.model.KlienResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KlienService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("api/klien")
    suspend fun getKlien(): KlienResponse

    @GET("api/klien/{id_klien}")
    suspend fun getKlienById(@Path("id_klien") id: Int): KlienResponseDetail

    @POST("api/klien/store")
    suspend fun insertKlien(@Body klien: Klien)

    @PUT("api/klien/{id_klien}")
    suspend fun updateKlien(@Path("id_klien") id: Int, @Body klien: Klien)

    @DELETE("api/klien/{id_klien}")
    suspend fun deleteKlien(@Path("id_klien") id: Int): Response<Void>
}
