package com.example.a6_uaspam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Vendor(
    @SerialName("id_vendor")
    val idV: Int? = null,

    @SerialName("nama_vendor")
    val namaV: String,

    @SerialName("jenis_vendor")
    val jenisV: String,

    @SerialName("telp_vendor")
    val telpV: String? = null,

    @SerialName("email_vendor")
    val emailV: String? = null,
)

@Serializable
data class VendorResponse(
    val status: Boolean,
    val message: String,
    val data: List<Vendor>
)

@Serializable
data class VendorResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Vendor
)