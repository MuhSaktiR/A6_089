package com.example.a6_uaspam.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

// Destinasi Untuk Acara
object DestinasiHomeAcr : DestinasiNavigasi {
    override val route = "home Acara"
    override val titleRes = "Manajemen Acara"
}

object DestinasiInsertAcr : DestinasiNavigasi {
    override val route = "insert Acara"
    override val titleRes = "Tambah Acara"
}

object DestinasiDetailAcr: DestinasiNavigasi{
    override val route = "detail Acara"
    override val titleRes = "Detail Acara"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiUpdateAcr: DestinasiNavigasi {
    override val route = "update Acara"
    override val titleRes = "Update Acara"
    const val ID = "ID"
    val routesWithArg = "$route/{$ID}"
}

// Destinasi Untuk Lokasi
object DestinasiHomeLks : DestinasiNavigasi {
    override val route = "home Lokasi"
    override val titleRes = "Manajemen Lokasi"
}

object DestinasiInsertLks : DestinasiNavigasi {
    override val route = "insert Lokasi"
    override val titleRes = "Tambah Lokasi"
}

object DestinasiDetailLks: DestinasiNavigasi{
    override val route = "detail Lokasi"
    override val titleRes = "Detail Lokasi"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiUpdateLks: DestinasiNavigasi {
    override val route = "update Lokasi"
    override val titleRes = "Update Lokasi"
    const val ID = "ID"
    val routesWithArg = "$route/{$ID}"
}

// Destinasi Untuk Klien
object DestinasiHomeKln : DestinasiNavigasi {
    override val route = "home Klien"
    override val titleRes = "Manajemen Klien"
}

object DestinasiInsertKln : DestinasiNavigasi {
    override val route = "insert Klien"
    override val titleRes = "Tambah Klien"
}

object DestinasiDetailKln: DestinasiNavigasi {
    override val route = "detail Klien"
    override val titleRes = "Detail Klien"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiUpdateKln: DestinasiNavigasi {
    override val route = "update Klien"
    override val titleRes = "Update Klien"
    const val ID = "ID"
    val routesWithArg = "$route/{$ID}"
}

// Destinasi Untuk Vendor
object DestinasiHomeVnd : DestinasiNavigasi {
    override val route = "home Vendor"
    override val titleRes = "Manajemen Vendor"
}

object DestinasiInsertVnd : DestinasiNavigasi {
    override val route = "insert Vendor"
    override val titleRes = "Tambah Vendor"
}

object DestinasiDetailVnd: DestinasiNavigasi {
    override val route = "detail Vendor"
    override val titleRes = "Detail Vendor"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}

object DestinasiUpdateVnd: DestinasiNavigasi {
    override val route = "update Vendor"
    override val titleRes = "Update Vendor"
    const val ID = "ID"
    val routesWithArg = "$route/{$ID}"
}



