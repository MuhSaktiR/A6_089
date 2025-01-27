package com.example.a6_uaspam.ui.customwidget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    showRefreshButton: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp) // Atur tinggi TopAppBar
            .padding(top = 30.dp),

        title = { Text(
            title,
            fontWeight = FontWeight.Bold,
            color = Color.White
        ) },
        actions = {
            if (showRefreshButton) { // Tampilkan tombol refresh jika true
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    tint = Color.White, // Menetapkan warna ikon menjadi putih
                    modifier = Modifier.clickable {
                        onRefresh()
                    }
                )
            }
        },

        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp,
                    modifier = modifier
                        .size(80.dp)
                        .padding(end = 30.dp)
                        .padding(bottom = 20.dp),) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF437bba), // Menetapkan warna latar belakang menjadi biru
            actionIconContentColor = Color.White, // Warna ikon action (refresh) menjadi putih
            navigationIconContentColor = Color.White // Warna ikon navigasi (back) menjadi putih
        )

    )
}
