package com.example.a6_uaspam.ui.view.acara

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_uaspam.model.Acara
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiDetailAcr
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.DetailAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.DetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenAcr(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAcrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailAcr.titleRes,
                canNavigateBack = true,
                showRefreshButton = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getAcaraById()
                }
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            DetailStatus(
                detailUiState = viewModel.detailUiState,
                retryAction = { viewModel.getAcaraById() }
            )
        }
    }
}


@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState,
    viewModel: DetailAcrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiState.Success -> {
            if (detailUiState.acr.data.id == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailAcr(
                    acara = detailUiState.acr.data,
                    modifier = modifier.fillMaxWidth(),
                    klienList = viewModel.klienList,
                    lokasiList = viewModel.lokasiList
                )

            }
        }

        is DetailUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailAcr(
    modifier: Modifier = Modifier,
    acara: Acara,
    klienList: List<Klien>,
    lokasiList: List<Lokasi>
) {
    // Cari nama dan telp klien berdasarkan idK
    val namaKlien = klienList.find { it.idK == acara.idK }?.namaK ?: "Tidak ada klien"
    val telpKlien = klienList.find { it.idK == acara.idK }?.telpK ?: ""
    val emailKlien = klienList.find { it.idK == acara.idK }?.emailK ?: ""

    // Cari nama lokasi dan alamat berdasarkan idL
    val namaLokasi = lokasiList.find { it.idL == acara.idL }?.namaL ?: "Tidak ada nama lokasi"
    val alamatLokasi = lokasiList.find { it.idL == acara.idL }?.alamatL ?: "Tidak ada alamat lokasi"
    val kapasitasLokasi = lokasiList.find { it.idL == acara.idL }?.kapasitas ?: 0

    Card(
        modifier = modifier
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFdee0df))
    ) {
        // Bungkus konten dengan Column yang dapat di-scroll
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()) // Tambahkan scroll di sini
        ) {
            Text(
                text = "Data Acara",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = modifier.padding(4.dp)
            )
            DetailAcr(judul = "ID", isinya = acara.id?.toString() ?: "Tidak ada ID")
            DetailAcr(judul = "Nama", isinya = acara.nama)
            DetailAcr(judul = "Deskripsi", isinya = acara.deskripsi)
            DetailAcr(judul = "Tanggal Mulai", isinya = acara.tanggalMulai)
            DetailAcr(judul = "Tanggal Selesai", isinya = acara.tanggalBerakhir)
            DetailAcr(judul = "Status", isinya = acara.status)
            Text(
                text = "Data Klien",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = modifier.padding(4.dp)
            )
            DetailAcr(judul = "Nama", isinya = namaKlien)
            DetailAcr(judul = "Nomor Hp", isinya = telpKlien)
            DetailAcr(judul = "Email", isinya = emailKlien)
            Text(
                text = "Data Lokasi",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = modifier.padding(4.dp)
            )
            DetailAcr(judul = "Nama", isinya = namaLokasi)
            DetailAcr(judul = "Alamat", isinya = alamatLokasi)
            DetailAcr(judul = "Kapasitas", isinya = kapasitasLokasi.toString())
        }
    }
}

@Composable
fun DetailAcr(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .weight(0.6f)
                .padding(4.dp)
                .background(Color(0xFF437bba), shape = MaterialTheme.shapes.small)
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = judul,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White // Change color as needed
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
                .background(Color.White, shape = MaterialTheme.shapes.small)
                .padding(4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = isinya,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black // Change color as needed
            )
        }
    }
}