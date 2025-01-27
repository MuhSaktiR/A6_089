package com.example.a6_uaspam.ui.view.lokasi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiInsertLks
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.FormErrorStateLks
import com.example.a6_uaspam.ui.viewmodel.lokasi.FormStateLks
import com.example.a6_uaspam.ui.viewmodel.lokasi.InsertLksViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.InsertUiEventLks
import com.example.a6_uaspam.ui.viewmodel.lokasi.InsertUiStateLks
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenLokasi(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertLksViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Observe formState to show success or error message
    val formState = viewModel.formStateLks
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect to add delay before hiding the message
    LaunchedEffect(formState) {
        if (formState is FormStateLks.Success || formState is FormStateLks.Error) {
            val message = when (formState) {
                is FormStateLks.Success -> "Data berhasil disimpan"
                is FormStateLks.Error -> formState.message
                else -> ""
            }

            // Show the snackbar message
            snackbarHostState.showSnackbar(message)

            if (formState is FormStateLks.Success) {
                delay(700) // Wait for 1 second before navigating back
                navigateBack() // Navigate back after the delay
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertLks.titleRes,
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
            EntryBodyLks(
                insertUiState = viewModel.uiStateLks,
                onLokasiValueChange = viewModel::updateInsertLksState,
                onSaveClick = {
                    if (viewModel.validateFields()) {
                        coroutineScope.launch {
                            viewModel.insertLks()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun EntryBodyLks(
    insertUiState: InsertUiStateLks,
    onLokasiValueChange: (InsertUiEventLks) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Form input untuk menerima data
        FormInputLks(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onLokasiValueChange,
            isEntryValid = insertUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
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
fun FormInputLks(
    insertUiEvent: InsertUiEventLks,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEventLks) -> Unit = {},
    enabled: Boolean = true,
    isEntryValid: FormErrorStateLks = FormErrorStateLks()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Nama Lokasi
        OutlinedTextField(
            value = insertUiEvent.namaL,
            onValueChange = { onValueChange(insertUiEvent.copy(namaL = it)) },
            label = { Text("Nama Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.namaL != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        // Supporting text (error message)
        isEntryValid.namaL?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Alamat Lokasi
        OutlinedTextField(
            value = insertUiEvent.alamatL,
            onValueChange = { onValueChange(insertUiEvent.copy(alamatL = it)) },
            label = { Text("Alamat Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.alamatL != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
            )
        // Supporting text (error message)
        isEntryValid.alamatL?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Kapasitas Lokasi
        OutlinedTextField(
            value = insertUiEvent.kapasitas?.toString() ?: "", // Tampilkan string kosong jika null
            onValueChange = { newValue ->
                val kapasitas = newValue.toIntOrNull() // Ubah ke Int jika valid
                onValueChange(insertUiEvent.copy(kapasitas = kapasitas))
            },
            label = { Text("Kapasitas Lokasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.kapasitas != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        // Supporting text (error message)
        isEntryValid.kapasitas?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }

}