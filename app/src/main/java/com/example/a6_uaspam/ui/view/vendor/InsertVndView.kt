package com.example.a6_uaspam.ui.view.vendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.example.a6_uaspam.ui.customwidget.Dropdown
import com.example.a6_uaspam.ui.navigation.DestinasiInsertVnd
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.vendor.FormErrorStateVnd
import com.example.a6_uaspam.ui.viewmodel.vendor.FormStateVnd
import com.example.a6_uaspam.ui.viewmodel.vendor.InsertUiEventVnd
import com.example.a6_uaspam.ui.viewmodel.vendor.InsertUiStateVnd
import com.example.a6_uaspam.ui.viewmodel.vendor.InsertVndViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenVendor(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertVndViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Observe formState to show success or error message
    val formState = viewModel.formStateVnd
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect to add delay before hiding the message
    LaunchedEffect(formState) {
        if (formState is FormStateVnd.Success || formState is FormStateVnd.Error) {
            val message = when (formState) {
                is FormStateVnd.Success -> "Data berhasil disimpan"
                is FormStateVnd.Error -> formState.message
                else -> ""
            }

            // Show the snackbar message
            snackbarHostState.showSnackbar(message)

            if (formState is FormStateVnd.Success) {
                delay(700) // Wait for 1 second before navigating back
                navigateBack() // Navigate back after the delay
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertVnd.titleRes,
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
            EntryBodyVnd(
                insertUiState = viewModel.uiStateVnd,
                onVendorValueChange = viewModel::updateInsertVndState,
                onSaveClick = {
                    if (viewModel.validateFields()) {
                        coroutineScope.launch {
                            viewModel.insertVnd()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun EntryBodyVnd(
    insertUiState: InsertUiStateVnd,
    onVendorValueChange: (InsertUiEventVnd) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Form input untuk menerima data
        FormInputVnd(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onVendorValueChange,
            isEntryValid = insertUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol Simpan dengan pengecekan data
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = insertUiState.isEntryValid.isValid() // Only enabled if form is valid
        ) {

            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputVnd(
    insertUiEvent: InsertUiEventVnd,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEventVnd) -> Unit = {},
    enabled: Boolean = true,
    isEntryValid: FormErrorStateVnd = FormErrorStateVnd()
) {
    // Daftar jenis layanan vendor
    val jenisLayananOptions = listOf(
        "Katering", "Dekorasi", "Audio Visual", "Penyewaan Peralatan", "Fotografi",
        "Videografi", "Makeup Artist", "Event Organizer", "Sewa Meja Kursi",
        "Penyewaan Sound System", "Penyewaan Lighting",
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Nama Vendor
        OutlinedTextField(
            value = insertUiEvent.namaV,
            onValueChange = { onValueChange(insertUiEvent.copy(namaV = it)) },
            label = { Text("Nama Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.namaV != null
        )
        isEntryValid.namaV?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Jenis Layanan Vendor
        Dropdown(
            selectedValue = insertUiEvent.jenisV,
            options = jenisLayananOptions,
            label = "Jenis Layanan Vendor",
            onValueChangedEvent = { newJenisLayanan ->
                onValueChange(insertUiEvent.copy(jenisV = newJenisLayanan))
            },
            isError = isEntryValid.jenisV != null,
            errorMessage = isEntryValid.jenisV,
            modifier = Modifier.fillMaxWidth()
        )

        // Nomor Telp Vendor
        OutlinedTextField(
            value = insertUiEvent.telpV,
            onValueChange = { onValueChange(insertUiEvent.copy(telpV = it)) },
            label = { Text("Nomor HP Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.telpV != null
        )
        isEntryValid.telpV?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Email Vendor
        OutlinedTextField(
            value = insertUiEvent.emailV,
            onValueChange = { onValueChange(insertUiEvent.copy(emailV = it)) },
            label = { Text("Email Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.emailV != null
        )
        isEntryValid.emailV?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }
}
