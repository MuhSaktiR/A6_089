package com.example.a6_uaspam.service

import com.example.a6_uaspam.model.Vendor
import com.example.a6_uaspam.model.VendorResponse
import com.example.a6_uaspam.model.VendorResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VendorService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("api/vendor")
    suspend fun getVendor(): VendorResponse

    @GET("api/vendor/{id_vendor}")
    suspend fun getVendorById(@Path("id_vendor") id: Int): VendorResponseDetail

    @POST("api/vendor/store")
    suspend fun insertVendor(@Body vendor: Vendor)

    @PUT("api/vendor/{id_vendor}")
    suspend fun updateVendor(@Path("id_vendor") id: Int, @Body vendor: Vendor)

    @DELETE("api/vendor/{id_vendor}")
    suspend fun deleteVendor(@Path("id_vendor") id: Int): Response<Void>
}
