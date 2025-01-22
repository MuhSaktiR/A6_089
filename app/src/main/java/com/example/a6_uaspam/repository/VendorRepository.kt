package com.example.a6_uaspam.repository

import com.example.a6_uaspam.model.Vendor
import com.example.a6_uaspam.model.VendorResponse
import com.example.a6_uaspam.model.VendorResponseDetail
import com.example.a6_uaspam.service.VendorService
import java.io.IOException

interface VendorRepository {
    suspend fun getVendor(): VendorResponse
    suspend fun insertVendor(vendor: Vendor)
    suspend fun updateVendor(id: Int, vendor: Vendor)
    suspend fun deleteVendor(id: Int)
    suspend fun getVendorById(id: Int): VendorResponseDetail
}

class NetworkVendorRepository(
    private val vendorApiService: VendorService
) : VendorRepository {

    override suspend fun getVendor(): VendorResponse {
        return vendorApiService.getVendor()
    }

    override suspend fun insertVendor(vendor: Vendor) {
        vendorApiService.insertVendor(vendor)
    }

    override suspend fun updateVendor(id: Int, vendor: Vendor) {
        vendorApiService.updateVendor(id, vendor)
    }

    override suspend fun deleteVendor(id: Int) {
        try {
            val response = vendorApiService.deleteVendor(id)
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

    override suspend fun getVendorById(id: Int): VendorResponseDetail {
        return vendorApiService.getVendorById(id)
    }
}
