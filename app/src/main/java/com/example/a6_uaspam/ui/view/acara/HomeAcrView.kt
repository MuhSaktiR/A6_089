package com.example.a6_uaspam.ui.view.acara

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a6_uaspam.R
import com.example.a6_uaspam.model.Acara



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
                verticalAlignment = Alignment.CenterVertically, // Posisikan informasi di tengah
            ) {
                // Icon besar di kiri
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
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Sebar tombol secara merata
            ) {
                Button(
                    onClick = { onDetailClick(acara) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF437bba)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Detail", color = Color.White, fontSize = 18.sp)
                }

                Button(
                    onClick = { onEditClick(acara) },
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