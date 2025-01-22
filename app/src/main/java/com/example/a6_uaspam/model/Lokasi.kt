package com.example.a6_uaspam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Lokasi(
    @SerialName("id_lokasi")
    val idL: Int? = null,

    @SerialName("nama_lokasi")
    val namaL: String,

    @SerialName("alamat_lokasi")
    val alamatL: String,

    val kapasitas: Int,
)

@Serializable
data class LokasiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Lokasi>
)

@Serializable
data class LokasiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Lokasi
)