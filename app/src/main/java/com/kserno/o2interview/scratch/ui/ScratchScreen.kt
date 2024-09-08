package com.kserno.o2interview.scratch.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kserno.o2interview.ui.Button
import com.kserno.o2interview.ui.Card
import com.kserno.o2interview.ui.FormattedString
import com.kserno.o2interview.ui.LoadingScreen
import com.kserno.o2interview.ui.Page
import com.kserno.o2interview.ui.SnackbarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScratchScreen(navController: NavController) {
    val viewModel: ScratchViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    ScratchScreen(
        title = viewModel.title,
        state = state,
        snackbarFlow = viewModel.snackbarFlow,
        loadingState = viewModel.loadingState,
        navController = navController,
    )
}

@Composable
private fun ScratchScreen(
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
        when (state) {
            is State.Content -> {
                Card(state = state.cardState)
                Button(state = state.scratchButton)
            }
            is State.Loading -> LoadingScreen()
        }
    }
}
