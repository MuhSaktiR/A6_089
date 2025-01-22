package com.example.a6_uaspam.service

import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.model.LokasiResponse
import com.example.a6_uaspam.model.LokasiResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LokasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("api/lokasi")
    suspend fun getLokasi(): LokasiResponse

    @GET("api/lokasi/{id_lokasi}")
    suspend fun getLokasiById(@Path("id_lokasi") id: Int): LokasiResponseDetail

    @POST("api/lokasi/store")
    suspend fun insertLokasi(@Body lokasi: Lokasi)

    @PUT("api/lokasi/{id_lokasi}")
    suspend fun updateLokasi(@Path("id_lokasi") id: Int, @Body lokasi: Lokasi)

    @DELETE("api/lokasi/{id_lokasi}")
    suspend fun deleteLokasi(@Path("id_lokasi") id: Int): Response<Void>
}
