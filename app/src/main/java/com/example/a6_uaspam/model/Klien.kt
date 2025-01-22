package com.example.a6_uaspam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Klien(
    @SerialName("id_klien")
    val idK: Int? = null,

    @SerialName("nama_klien")
    val namaK: String,

    @SerialName("telp_klien")
    val telpK: String? = null,

    @SerialName("email_klien")
    val emailK: String? = null,
)

@Serializable
data class KlienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Klien>
)

@Serializable
data class KlienResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Klien
)