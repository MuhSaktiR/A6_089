package com.example.a6_uaspam.ui.view.lokasi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiDetailLks
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.DetailLksViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.DetailUiStateLks

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenLks(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailLksViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailLks.titleRes,
                canNavigateBack = true,
                showRefreshButton = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getLokasiById()
                }
            )
        },
    ) { innerPadding ->
        DetailStatusLks(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.detailUiStateLks,
            retryAction = { viewModel.getLokasiById() }
        )
    }
}

@Composable
fun DetailStatusLks(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiStateLks
) {
    when (detailUiState) {
        is DetailUiStateLks.Loading -> com.example.a6_uaspam.ui.view.acara.OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiStateLks.Success -> {
            if (detailUiState.lks.data.idL == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailLks(
                    lokasi = detailUiState.lks.data,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailUiStateLks.Error -> com.example.a6_uaspam.ui.view.acara.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailLks(
    modifier: Modifier = Modifier,
    lokasi: Lokasi
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFdee0df)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            DetailLks(judul = "ID Lokasi", isinya = lokasi.idL?.toString() ?: "Tidak ada ID")
            DetailLks(judul = "Nama Lokasi", isinya = lokasi.namaL)
            DetailLks(judul = "Alamat Lokasi", isinya = lokasi.alamatL)
            DetailLks(judul = "Kapasitas Lokasi", isinya = lokasi.kapasitas.toString())
        }
    }
}

@Composable
fun DetailLks(
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
                .weight(0.7f)
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