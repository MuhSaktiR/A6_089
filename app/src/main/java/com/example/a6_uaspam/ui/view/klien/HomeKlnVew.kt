package com.example.a6_uaspam.ui.view.klien

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.a6_uaspam.R
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiHomeKln
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.HomeKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.HomeUiStateK

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenKlien(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    onManajemenAcaraClick: () -> Unit = {},
    onManajemenLokasiClick: () -> Unit = {},
    onManajemenVendorClick: () -> Unit = {},
    viewModel: HomeKlnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKln.titleRes,
                canNavigateBack = false,
                showRefreshButton = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getKln() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF437bba)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Klien",
                    tint = Color.White)
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Manajemen Section
            ManajemenSection(
                onManajemenAcaraClick = onManajemenAcaraClick,
                onManajemenLokasiClick = onManajemenLokasiClick,
                onManajemenVendorClick = onManajemenVendorClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Daftar Klien
            HomeStatusKlien(
                homeUiState = viewModel.klnUIState,
                retryAction = { viewModel.getKln() },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Menambahkan weight agar bisa mengisi ruang yang tersisa
                onDetailClick = onDetailClick,
                onEditClick = onEditClick,
                onDeleteClick = {
                    viewModel.deleteKln(it.idK ?: 0)
                    viewModel.getKln()
                }
            )
        }
    }
}

@Composable
fun HomeStatusKlien(
    homeUiState: HomeUiStateK,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Klien) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
){
    when (homeUiState) {
        is HomeUiStateK.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiStateK.Success ->
            if (homeUiState.klien.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Klien" )
                }
            } else {
                KlnLayout(
                    klien = homeUiState.klien, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idK ?: 0)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it.idK ?: 0)
                    }
                )
            }
        is HomeUiStateK.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ManajemenSection(
    onManajemenAcaraClick: () -> Unit,
    onManajemenLokasiClick: () -> Unit,
    onManajemenVendorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentIndex = remember { mutableStateOf(0) } // Untuk melacak kategori yang sedang ditampilkan

    // Daftar tombol manajemen
    val manajemenItems = listOf("Manajemen Vendor", "Manajemen Lokasi", "Manajemen Acara")
    val onClickActions = listOf(onManajemenVendorClick, onManajemenLokasiClick, onManajemenAcaraClick)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Judul untuk kategori yang sedang ditampilkan
        Text(
            text = "Manajemen Kategori",
            style = MaterialTheme.typography.titleMedium,
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
fun OnLoading(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun KlnLayout(
    klien: List<Klien>,
    modifier: Modifier = Modifier,
    onEditClick: (Klien) -> Unit,
    onDetailClick: (Klien) -> Unit,
    onDeleteClick: (Klien) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(), // Pastikan LazyColumn mengisi ruang yang tersedia
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = klien,
            itemContent = { klien ->
                KlnCard(
                    klien = klien,
                    modifier = Modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(klien) },
                    onEditClick = { onEditClick(klien) },
                    onDeleteClick = { onDeleteClick(klien) }
                )
            }
        )
    }
}

@Composable
fun KlnCard(
    klien: Klien,
    modifier: Modifier = Modifier,
    onDetailClick: (Klien) -> Unit = {},
    onEditClick: (Klien) -> Unit = {},
    onDeleteClick: (Klien) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(klien)
            },
            onDeleteCancel = {
                showDialog = false
            }
        )
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
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
                verticalAlignment = Alignment.CenterVertically // Posisikan informasi di tengah
            ) {
                // Icon besar di kiri
                Box(
                    modifier = Modifier
                        .size(100.dp) // Ukuran ikon besar tetap kecil
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.klien),
                        contentDescription = "Icon Klien",
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
                        text = klien.namaK,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "ID Klien Icon",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ID Klien".padEnd(12) + ": " +  klien.idK,
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
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onDetailClick(klien) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF437bba)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Detail", color = Color.White, fontSize = 18.sp)
                }

                Button(
                    onClick = { onEditClick(klien) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFc7b648)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit", color = Color.White, fontSize = 18.sp)
                }

                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFba4b43)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete", color = Color.White, fontSize = 18.sp)
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