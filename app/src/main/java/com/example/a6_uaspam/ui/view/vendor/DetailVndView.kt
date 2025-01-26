package com.example.a6_uaspam.ui.view.vendor

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
import com.example.a6_uaspam.model.Vendor
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiDetailVnd
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.vendor.DetailUiStateVnd
import com.example.a6_uaspam.ui.viewmodel.vendor.DetailVndViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenVnd(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailVndViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailVnd.titleRes,
                canNavigateBack = true,
                showRefreshButton = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getVendorById()
                }
            )
        },
    ) { innerPadding ->
        DetailStatusVnd(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.detailUiStateVnd,
            retryAction = { viewModel.getVendorById() }
        )
    }
}

@Composable
fun DetailStatusVnd(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailUiStateVnd
) {
    when (detailUiState) {
        is DetailUiStateVnd.Loading -> com.example.a6_uaspam.ui.view.acara.OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiStateVnd.Success -> {
            if (detailUiState.vnd.data.idV == 0) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailVnd(
                    vendor = detailUiState.vnd.data,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailUiStateVnd.Error -> com.example.a6_uaspam.ui.view.acara.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailVnd(
    modifier: Modifier = Modifier,
    vendor: Vendor
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
            DetailVnd(judul = "ID Vendor", isinya = vendor.idV?.toString() ?: "Tidak ada ID")
            DetailVnd(judul = "Nama Vendor", isinya = vendor.namaV)
            DetailVnd(judul = "Jenis Layanan", isinya = vendor.jenisV)
            DetailVnd(judul = "Nomor Hp Vendor", isinya = vendor.telpV ?: "")
            DetailVnd(judul = "Email Vendor", isinya = vendor.emailV ?: "")
        }
    }
}

@Composable
fun DetailVnd(
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
                .weight(0.8f)
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