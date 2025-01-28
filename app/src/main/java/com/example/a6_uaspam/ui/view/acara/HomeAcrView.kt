package com.example.a6_uaspam.ui.view.acara

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.a6_uaspam.model.Acara
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.HomeAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.HomeUiState
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenAcara(
    navigateToItemEntry: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    onManajemenLokasiClick: () -> Unit = {},
    onManajemenKlienClick: () -> Unit = {},
    onManajemenVendorClick: () -> Unit = {},
    viewModel: HomeAcrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF1A9AF2), // rgba(26,164,242)
            Color(0xFF2C83B1), // rgba(44,131,177)
            Color(0xFF554B95)  // rgba(85,75,149)
        ),
        start = Offset(0f, 0f),  // Gradient posisi awal
        end = Offset(1000f, 1000f) // Gradient posisi akhir (209 degrees)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        // Top section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .padding(top = 40.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Sakti App",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )

                    Text(
                        text = "Manajemen Acara ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.acarautama),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(730.dp)
                .clip(RoundedCornerShape(topStart = 70.dp, topEnd = 70.dp))
                .background(Color.White)
                .align(Alignment.BottomEnd)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Manajemen Section
            ManajemenSection(
                onManajemenLokasiClick = onManajemenLokasiClick,
                onManajemenKlienClick = onManajemenKlienClick,
                onManajemenVendorClick = onManajemenVendorClick
            )

            // Daftar Acara
            HomeStatusAcara(
                homeUiState = viewModel.acrUIState,
                retryAction = { viewModel.getAcr() },
                modifier = Modifier
                    .fillMaxWidth(),
                onDetailClick = onDetailClick,
                onEditClick = onEditClick,
                onDeleteClick = {
                    viewModel.deleteAcr(it.id ?: 0)
                    viewModel.getAcr()
                }
            )
        }

        // Adding the Floating Action Button at the bottom
        FloatingActionButton(
            onClick = navigateToItemEntry,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(25.dp)
                .padding(end = 10.dp)
                .padding(bottom = 105.dp)
                .align(Alignment.BottomEnd)
                .width(140.dp) // Atur lebar tombol
                .height(40.dp), // Atur tinggi tombol
            containerColor = Color(0xFF437bba)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, // Menyusun ikon dan teks di tengah secara vertikal
                horizontalArrangement = Arrangement.Center // Menyusun isi row secara horisontal
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Acara", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp)) // Memberi jarak antara ikon dan teks
                Text(
                    text = "Tambah Acara",
                    fontSize = 12.sp, // Ukuran font yang lebih besar agar teks bisa panjang
                    color = Color.White
                )
            }
        }

        // Bottom Navigation Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .background(gradient)
                .align(Alignment.BottomCenter),
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
                    IconButton(onClick = { navigateToHome() }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = "Halaman Utama", tint = Color.Black)
                    }
                }

                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notif", tint = Color.White)
                }
            }
        }
    }
}


@Composable
fun HomeStatusAcara(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Acara) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
){
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success ->
            if (homeUiState.acara.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Acara" )
                }
            } else {
                AcrLayout(
                    acara = homeUiState.acara, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id ?: 0)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it.id ?: 0)
                    }
                )
            }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ManajemenSection(
    onManajemenLokasiClick: () -> Unit,
    onManajemenKlienClick: () -> Unit,
    onManajemenVendorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentIndex = remember { mutableStateOf(0) } // Untuk melacak kategori yang sedang ditampilkan

    // Daftar tombol manajemen
    val manajemenItems = listOf("Manajemen Lokasi", "Manajemen Klien", "Manajemen Vendor")
    val onClickActions = listOf(onManajemenLokasiClick, onManajemenKlienClick, onManajemenVendorClick)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Judul untuk kategori yang sedang ditampilkan
        Text(
            text = "Manajemen Kategori",
            fontSize = 20.sp,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Baris untuk navigasi kategori
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Tombol Navigasi Kiri
            IconButton(
                onClick = {
                    if (currentIndex.value > 0) {
                        currentIndex.value -= 1
                    }
                },
                enabled = currentIndex.value > 0
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Scroll Left",
                    tint = if (currentIndex.value > 0) Color(0xFF437bba) else Color.Gray
                )
            }

            // Tombol kategori aktif dengan warna kustom
            Button(
                onClick = onClickActions[currentIndex.value],
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF437bba))
            ) {
                Text(
                    text = manajemenItems[currentIndex.value],
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Tombol Navigasi Kanan
            IconButton(
                onClick = {
                    if (currentIndex.value < manajemenItems.size - 1) {
                        currentIndex.value += 1
                    }
                },
                enabled = currentIndex.value < manajemenItems.size - 1
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Scroll Right",
                    tint = if (currentIndex.value < manajemenItems.size - 1) Color(0xFF437bba) else Color.Gray
                )
            }
        }
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
fun AcrLayout(
    acara: List<Acara>,
    modifier: Modifier = Modifier,
    onEditClick: (Acara) -> Unit,
    onDetailClick: (Acara) -> Unit,
    onDeleteClick: (Acara) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 420.dp), // Batas maksimal tinggi
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = acara,
            itemContent = { acara ->
                AcrCard(
                    acara = acara,
                    modifier = Modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(acara) },
                    onEditClick = { onEditClick(acara) },
                    onDeleteClick = { onDeleteClick(acara) }
                )
            }
        )
    }
}

@Composable
fun AcrCard(
    acara: Acara,
    modifier: Modifier = Modifier,
    onDetailClick: (Acara) -> Unit = {},
    onEditClick: (Acara) -> Unit = {},
    onDeleteClick: (Acara) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(acara)
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
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Bagian informasi
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically, // Posisikan informasi di tengah
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp) // Ukuran ikon besar tetap kecil
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.acara),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(90.dp))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Informasi di kanan
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp), // Spasi antar baris
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = acara.nama,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Tanggal Mulai Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Mulai".padEnd(12) + ": " +  acara.tanggalMulai,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Tanggal Berakhir Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Berakhir".padEnd(10) + ": " +  acara.tanggalBerakhir,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Status Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text =  "Status".padEnd(11) + ": " + acara.status,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Bagian tombol
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Sebar tombol secara merata
            ) {
                Button(
                    onClick = { onDetailClick(acara) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF437bba)),
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text("Detail", color = Color.White, fontSize = 14.sp)
                }

                Button(
                    onClick = { onEditClick(acara) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc7b648)),
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text("Edit", color = Color.White, fontSize = 14.sp)
                }

                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFba4b43)),
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text("Delete", color = Color.White, fontSize = 14.sp)
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