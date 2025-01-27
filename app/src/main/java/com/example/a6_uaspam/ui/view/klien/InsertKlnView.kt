package com.example.a6_uaspam.ui.view.klien

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
import com.example.a6_uaspam.ui.navigation.DestinasiInsertKln
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.FormErrorStateKln
import com.example.a6_uaspam.ui.viewmodel.klien.FormStateKln
import com.example.a6_uaspam.ui.viewmodel.klien.InsertKlnViewModel
import com.example.a6_uaspam.ui.viewmodel.klien.InsertUiEventKln
import com.example.a6_uaspam.ui.viewmodel.klien.InsertUiStateKln
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenKlien(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKlnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Observe formState to show success or error message
    val formState = viewModel.formStateKln
    val snackbarHostState = remember { SnackbarHostState() }

    // LaunchedEffect to add delay before hiding the message
    LaunchedEffect(formState) {
        if (formState is FormStateKln.Success || formState is FormStateKln.Error) {
            val message = when (formState) {
                is FormStateKln.Success -> "Data berhasil disimpan"
                is FormStateKln.Error -> formState.message
                else -> ""
            }

            // Show the snackbar message
            snackbarHostState.showSnackbar(message)

            if (formState is FormStateKln.Success) {
                delay(700) // Wait for 1 second before navigating back
                navigateBack() // Navigate back after the delay
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertKln.titleRes,
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
            EntryBodyKln(
                insertUiState = viewModel.uiStateKln,
                onKlienValueChange = viewModel::updateInsertKlnState,
                onSaveClick = {
                    if (viewModel.validateFields()) {
                        coroutineScope.launch {
                            viewModel.insertKln()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun EntryBodyKln(
    insertUiState: InsertUiStateKln,
    onKlienValueChange: (InsertUiEventKln) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Form input untuk menerima data
        FormInputKln(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onKlienValueChange,
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
fun FormInputKln(
    insertUiEvent: InsertUiEventKln,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEventKln) -> Unit = {},
    enabled: Boolean = true,
    isEntryValid: FormErrorStateKln = FormErrorStateKln()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Nama Klien
        OutlinedTextField(
            value = insertUiEvent.namaK,
            onValueChange = { onValueChange(insertUiEvent.copy(namaK = it)) },
            label = { Text("Nama Klien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.namaK != null,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        // Supporting text (error message)
        isEntryValid.namaK?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Nomor Telp Klien
        OutlinedTextField(
            value = insertUiEvent.telpK,
            onValueChange = { onValueChange(insertUiEvent.copy(telpK = it)) },
            label = { Text("Nomor HP Klien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.telpK != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        // Supporting text (error message)
        isEntryValid.telpK?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        // Email Klien
        OutlinedTextField(
            value = insertUiEvent.emailK,
            onValueChange = { onValueChange(insertUiEvent.copy(emailK = it)) },
            label = { Text("Email Klien") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = isEntryValid.emailK != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF437bba),
                focusedLabelColor = Color(0xFF437bba))
        )
        // Supporting text (error message)
        isEntryValid.emailK?.let {
            Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }
    }
}