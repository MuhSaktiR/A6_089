package com.example.a6_uaspam.ui.view.acara

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_uaspam.model.Klien
import com.example.a6_uaspam.model.Lokasi
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.customwidget.Dropdown
import com.example.a6_uaspam.ui.navigation.DestinasiInsertAcr
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.FormErrorState
import com.example.a6_uaspam.ui.viewmodel.acara.FormState
import com.example.a6_uaspam.ui.viewmodel.acara.InsertAcrViewModel
import com.example.a6_uaspam.ui.viewmodel.acara.InsertUiEvent
import com.example.a6_uaspam.ui.viewmodel.acara.InsertUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenAcara(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAcrViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Observe formState to show success or error message
    val formState = viewModel.formState
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect to add delay before hiding the message
    LaunchedEffect(formState) {
        if (formState is FormState.Success || formState is FormState.Error) {
            val message = when (formState) {
                is FormState.Success -> "Data berhasil disimpan"
                is FormState.Error -> formState.message
                else -> ""
            }

            // Show the snackbar message
            snackbarHostState.showSnackbar(message)

            if (formState is FormState.Success) {
                delay(700) // Wait for 1 second before navigating back
                navigateBack() // Navigate back after the delay
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertAcr.titleRes,
                canNavigateBack = true,
                showRefreshButton = false,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Add SnackbarHost here
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            EntryBody(
                insertUiState = viewModel.uiState,
                onAcaraValueChange = viewModel::updateInsertAcrState,
                onSaveClick = {
                    if (viewModel.validateFields()) {
                        coroutineScope.launch {
                            viewModel.insertAcr()
                        }
                    }
                },
                klienList = viewModel.klienList,
                lokasiList = viewModel.lokasiList
            )
        }
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onAcaraValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    klienList: List<Klien>, // Dapatkan data klien dari ViewModel
    lokasiList: List<Lokasi> // Dapatkan data lokasi dari ViewModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Form input untuk menerima data
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAcaraValueChange,
            isEntryValid = insertUiState.isEntryValid,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            lokasiList = lokasiList,
            klienList = klienList,
            insertUiState = insertUiState
        )

        // Tombol Simpan dengan pengecekan data
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.isEntryValid.isValid(), // Only enabled if form is valid
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF437bba))
        ) {

            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    insertUiState: InsertUiState,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true,
    isEntryValid: FormErrorState = FormErrorState(),
    klienList: List<Klien>,
    lokasiList: List<Lokasi>
) {
    // Daftar status acara
    val statusOptions = listOf("Direncanakan", "Berlangsung", "Selesai")

    // State untuk tanggal mulai dan berakhir
    val tanggalMulai = remember { mutableStateOf(insertUiState.insertUiEvent.tanggalMulai) }
    val tanggalBerakhir = remember { mutableStateOf(insertUiState.insertUiEvent.tanggalBerakhir) }

    // Menggunakan LaunchedEffect untuk memperbarui tanggal saat insertUiState berubah
    LaunchedEffect(insertUiState) {
        tanggalMulai.value = insertUiState.insertUiEvent.tanggalMulai
        tanggalBerakhir.value = insertUiState.insertUiEvent.tanggalBerakhir
    }

    val context = LocalContext.current

    // Fungsi untuk mengatur tanggal dan waktu mulai
    val onDateMulaiSet = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val date = "$dayOfMonth-${month + 1}-$year"
        val updatedDate = "$date ${tanggalMulai.value.substringAfterLast(" ")}"
        tanggalMulai.value = updatedDate
        onValueChange(insertUiEvent.copy(tanggalMulai = updatedDate))
    }

    val onTimeMulaiSet = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val time = "$hourOfDay:$minute"
        val updatedDate = "${tanggalMulai.value.substringBefore(" ")} $time"
        tanggalMulai.value = updatedDate
        onValueChange(insertUiEvent.copy(tanggalMulai = updatedDate))
    }

    // Fungsi untuk mengatur tanggal dan waktu berakhir
    val onDateBerakhirSet = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val date = "$dayOfMonth-${month + 1}-$year"
        val waktuBerakhir = tanggalBerakhir.value.substringAfterLast(" ")
        val updatedDate = "$date $waktuBerakhir"

        val tanggalMulaiDate = tanggalMulai.value.substringBefore(" ")
        val waktuMulai = tanggalMulai.value.substringAfterLast(" ")

        // Validasi tanggal dan waktu
        if (date > tanggalMulaiDate || (date == tanggalMulaiDate && waktuBerakhir >= waktuMulai)) {
            tanggalBerakhir.value = updatedDate
            onValueChange(insertUiEvent.copy(tanggalBerakhir = updatedDate))
        } else {
            Toast.makeText(context, "Tanggal Berakhir tidak boleh kurang dari Tanggal Mulai", Toast.LENGTH_SHORT).show()
        }
    }

    val onTimeBerakhirSet = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val time = "$hourOfDay:$minute"
        val tanggalBerakhirDate = tanggalBerakhir.value.substringBefore(" ")
        val updatedDate = "$tanggalBerakhirDate $time"

        val tanggalMulaiDate = tanggalMulai.value.substringBefore(" ")
        val waktuMulai = tanggalMulai.value.substringAfterLast(" ")

        // Validasi tanggal dan waktu
        if (tanggalBerakhirDate > tanggalMulaiDate || (tanggalBerakhirDate == tanggalMulaiDate && time >= waktuMulai)) {
            tanggalBerakhir.value = updatedDate
            onValueChange(insertUiEvent.copy(tanggalBerakhir = updatedDate))
        } else {
            Toast.makeText(context, "Waktu Berakhir tidak boleh kurang dari Waktu Mulai", Toast.LENGTH_SHORT).show()
        }
    }

    //Tanggal dan Waktu Mulai dan Berakhir
    val datePickerDialogMulai = DatePickerDialog(
        context,
        onDateMulaiSet,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialogMulai = TimePickerDialog(
        context,
        onTimeMulaiSet,
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        true
    )

    val datePickerDialogBerakhir = DatePickerDialog(
        context,
        onDateBerakhirSet,
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialogBerakhir = TimePickerDialog(
        context,
        onTimeBerakhirSet,
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        true
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        // Nama Acara
        OutlinedTextField(
            value = insertUiEvent.nama,
            onValueChange = { onValueChange(insertUiEvent.copy(nama = it)) },
            label = { Text("Nama Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.nama != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        isEntryValid.nama?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Deskripsi Acara
        OutlinedTextField(
            value = insertUiEvent.deskripsi,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi = it)) },
            label = { Text("Deskripsi Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.deskripsi != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        isEntryValid.deskripsi?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Tanggal Mulai Acara
        OutlinedTextField(
            value = tanggalMulai.value,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalMulai = it)) },
            label = { Text("Tanggal Mulai Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialogMulai.show()
                    timePickerDialogMulai.show()
                }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Pilih Tanggal dan Waktu Mulai")
                }
            },
            isError = isEntryValid.tanggalMulai != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba)
            )
        )
        isEntryValid.tanggalMulai?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Tanggal Berakhir Acara
        OutlinedTextField(
            value = tanggalBerakhir.value,
            onValueChange = {
                if (tanggalBerakhir.value != tanggalMulai.value) {
                    onValueChange(insertUiEvent.copy(tanggalBerakhir = it))
                }
            },
            label = { Text("Tanggal Berakhir Acara") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    datePickerDialogBerakhir.show()
                    timePickerDialogBerakhir.show()
                }) {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Pilih Tanggal dan Waktu Berakhir")
                }
            },
            isError = isEntryValid.tanggalBerakhir != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba)
            )
        )
        isEntryValid.tanggalBerakhir?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Dropdown untuk Status Acara
        Dropdown(
            selectedValue = insertUiEvent.status,
            options = statusOptions,
            label = "Status Acara",
            onValueChangedEvent = { newStatus -> onValueChange(insertUiEvent.copy(status = newStatus)) },
            isError = isEntryValid.status != null,
            errorMessage = isEntryValid.status,
        )

        // Dropdown untuk Klien
        Dropdown(
            selectedValue = klienList.find { it.idK == insertUiState.insertUiEvent.idK }?.namaK ?: "",
            options = klienList.map { it.namaK },
            label = "Pilih Klien",
            onValueChangedEvent = { newName ->
                val selectedKlien = klienList.firstOrNull { it.namaK == newName }
                selectedKlien?.let {
                    onValueChange(insertUiState.insertUiEvent.copy(idK = it.idK))
                }
            },
            isError = insertUiState.isEntryValid.idK != null,
            errorMessage = insertUiState.isEntryValid.idK
        )

        // Dropdown untuk Lokasi
        Dropdown(
            selectedValue = lokasiList.find { it.idL == insertUiState.insertUiEvent.idL }?.namaL ?: "",
            options = lokasiList.map { it.namaL },
            label = "Pilih Lokasi",
            onValueChangedEvent = { newName ->
                val selectedLokasi = lokasiList.firstOrNull { it.namaL == newName }
                selectedLokasi?.let {
                    onValueChange(insertUiState.insertUiEvent.copy(idL = it.idL))
                }
            },
            isError = insertUiState.isEntryValid.idL != null,
            errorMessage = insertUiState.isEntryValid.idL
        )
    }
}