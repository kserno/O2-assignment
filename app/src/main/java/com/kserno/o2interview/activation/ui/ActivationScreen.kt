package com.kserno.o2interview.activation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kserno.o2interview.ui.Button
import com.kserno.o2interview.ui.FormattedString
import com.kserno.o2interview.ui.Page
import com.kserno.o2interview.ui.SnackbarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ActivationScreen(navController: NavController) {
    val viewModel: ActivationViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    ActivationScreen(
        title = viewModel.title,
        state = state,
        snackbarFlow = viewModel.snackbarFlow,
        loadingState = viewModel.loadingState,
        navController = navController,
    )
}

@Composable
private fun ActivationScreen(
    title: FormattedString,
    state: State,
    snackbarFlow: Flow<SnackbarEvent>,
    loadingState: StateFlow<Boolean>,
    navController: NavController,
) {
    Page(
        title = title,
        snackbarFlow = snackbarFlow,
        loadingState = loadingState,
        navController = navController,
    ) {
        Button(state = state.activationButton)
    }
}
