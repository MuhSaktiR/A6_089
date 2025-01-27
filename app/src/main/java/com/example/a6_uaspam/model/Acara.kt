package com.example.a6_uaspam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Acara(
    @SerialName("id_acara")
    val id: Int? = null,

    @SerialName("nama_acara")
    val nama: String,

    @SerialName("deskripsi_acara")
    val deskripsi: String,

    @SerialName("tanggal_mulai")
    val tanggalMulai: String,

    @SerialName("tanggal_berakhir")
    val tanggalBerakhir: String,

    @SerialName("status_acara")
    val status: String,

    @SerialName("id_klien")
    val idK: Int? = null,

    @SerialName("id_lokasi")
    val idL: Int? = null,

)

@Serializable
data class AcaraResponse(
    val status: Boolean,
    val message: String,
    val data: List<Acara>
)

@Serializable
data class AcaraResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Acara
)