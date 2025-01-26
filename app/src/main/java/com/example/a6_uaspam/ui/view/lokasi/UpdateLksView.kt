package com.example.a6_uaspam.ui.view.lokasi

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a6_uaspam.ui.customwidget.CostumeTopAppBar
import com.example.a6_uaspam.ui.navigation.DestinasiUpdateLks
import com.example.a6_uaspam.ui.viewmodel.PenyediaViewModel
import com.example.a6_uaspam.ui.viewmodel.lokasi.UpdateLksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenLks(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateLksViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateLks.titleRes,
                canNavigateBack = true,
                showRefreshButton = false,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodyLks(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiStateLks,
            onLokasiValueChange = viewModel::updateInsertLksState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateLks()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}