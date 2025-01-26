package com.example.a6_uaspam.ui.view.klien

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
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiDetailKln
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.DetailKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.DetailUiStateKln

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenKln(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailKlnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailKln.titleRes,
                canNavigateBack = true,
                showRefreshButton = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getKlienById()
                }
            )
        },
    ) { innerPadding ->
        DetailStatusKln(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.detailUiStateKln,
            retryAction = { viewModel.getKlienById() }
        )
    }
}

@Composable
fun DetailStatusKln(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiStateKln
) {
    when (detailUiState) {
        is DetailUiStateKln.Loading -> com.example.a6_uaspam.ui.view.acara.OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiStateKln.Success -> {
            if (detailUiState.kln.data.idK == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailKln(
                    klien = detailUiState.kln.data,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailUiStateKln.Error -> com.example.a6_uaspam.ui.view.acara.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailKln(
    modifier: Modifier = Modifier,
    klien: Klien
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
            DetailKln(judul = "ID Klien", isinya = klien.idK?.toString() ?: "Tidak ada ID")
            DetailKln(judul = "Nama Klien", isinya = klien.namaK)
            DetailKln(judul = "Nomor Hp Klien", isinya = klien.telpK ?: "")
            DetailKln(judul = "Email Klien", isinya = klien.emailK ?: "")
        }
    }
}

@Composable
fun DetailKln(
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