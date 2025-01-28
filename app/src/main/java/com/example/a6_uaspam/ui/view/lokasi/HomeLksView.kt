package com.example.a6_uaspam.ui.view.lokasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.a6_uaspam.R
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiHomeLks
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.HomeLksViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.HomeUiStateL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenLokasi(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateHome: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    navigateBack: () -> Unit ={},
    viewModel: HomeLksViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF1A9AF2),
            Color(0xFF2C83B1),
            Color(0xFF554B95)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeLks.titleRes,
                navigateUp = navigateBack,
                canNavigateBack = true,
                showRefreshButton = true,
                onRefresh = { viewModel.getLks() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(18.dp)
                    .padding(bottom = 21.dp)
                    .width(141.dp)
                    .height(40.dp),
                containerColor = Color(0xFF437bba)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tambah Lokasi",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(gradient),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Kalender", tint = Color.White)
                    }

                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { onNavigateHome() }) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Halaman Utama", tint = Color.Black)
                        }
                    }

                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notif", tint = Color.White)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Spacer(modifier = Modifier.height(16.dp))

            // Daftar Lokasi
            HomeStatusLokasi(
                homeUiState = viewModel.lksUIState,
                retryAction = { viewModel.getLks() },
                modifier = Modifier
                    .fillMaxWidth(),
                onDetailClick = onDetailClick,
                onEditClick = onEditClick,
                onDeleteClick = {
                    viewModel.deleteLks(it.idL ?: 0)
                    viewModel.getLks()
                }
            )
        }
    }
}

@Composable
fun HomeStatusLokasi(
    homeUiState: HomeUiStateL,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
){
    when (homeUiState) {
        is HomeUiStateL.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStateL.Success ->
            if (homeUiState.lokasi.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Lokasi" )
                }
            } else {
                LksLayout(
                    lokasi = homeUiState.lokasi, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idL ?: 0)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it.idL ?: 0)
                    }
                )
            }
        is HomeUiStateL.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    // Memuat file Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    // Menjalankan animasi
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 1.5f,
        iterations = LottieConstants.IterateForever // Animasi berjalan terus-menerus
    )

    // Menampilkan animasi
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(50.dp)
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Menggunakan animasi Lottie untuk menggantikan gambar error
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.erroranimation1))

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever // Animasi terus berulang
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(250.dp) // Sesuaikan ukuran
        )

        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(4.dp)
        )
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF437bba),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun LksLayout(
    lokasi: List<Lokasi>,
    modifier: Modifier = Modifier,
    onEditClick: (Lokasi) -> Unit,
    onDetailClick: (Lokasi) -> Unit,
    onDeleteClick: (Lokasi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = lokasi,
            itemContent = { lokasi ->
                LksCard(
                    lokasi = lokasi,
                    modifier = Modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(lokasi) },
                    onEditClick = { onEditClick(lokasi) },
                    onDeleteClick = { onDeleteClick(lokasi) }
                )
            }
        )
    }
}

@Composable
fun LksCard(
    lokasi: Lokasi,
    modifier: Modifier = Modifier,
    onDetailClick: (Lokasi) -> Unit = {},
    onEditClick: (Lokasi) -> Unit = {},
    onDeleteClick: (Lokasi) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(lokasi)
            },
            onDeleteCancel = {
                showDialog = false
            }
        )
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(horizontal = 25.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFf5f2f2))
    ) {
        Column {
            // Bagian informasi
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically // Posisikan informasi di tengah
            ) {
                // Icon besar di kiri
                Box(
                    modifier = Modifier
                        .padding(start = 18.dp)
                        .size(80.dp) // Ukuran ikon besar tetap kecil
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lokasi),
                        contentDescription = "Icon Klien",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(90.dp))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Informasi di kanan
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp), // Spasi antar baris
                    modifier = Modifier
                        .padding(start = 25.dp)
                        .weight(0.4f)
                ) {
                    Text(
                        text = lokasi.namaL,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Tanggal Mulai Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Kapasitas".padEnd(12) + ": " +  lokasi.kapasitas,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Bagian tombol
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onDetailClick(lokasi) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF437bba)),
                    modifier = Modifier
                        .height(35.dp)
                        .width(90.dp)
                ) {
                    Text("Detail", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onEditClick(lokasi) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc7b648)),
                    modifier = Modifier
                        .height(35.dp)
                        .width(90.dp)
                ) {
                    Text("Edit", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFba4b43)),
                    modifier = Modifier
                        .height(35.dp)
                        .width(90.dp)
                ) {
                    Text("Delete", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    title: String = "Hapus Data",
    text: String = "Apakah anda yakin ingin menghapus data?",
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(title) },
        text = { Text(text) },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}