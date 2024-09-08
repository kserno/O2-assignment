package com.kserno.o2interview.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.kserno.o2interview.AppNavGraph
import com.kserno.o2interview.ui.Button
import com.kserno.o2interview.ui.Card
import com.kserno.o2interview.ui.FormattedString
import com.kserno.o2interview.ui.LoadingScreen
import com.kserno.o2interview.ui.ObserveEvents
import com.kserno.o2interview.ui.Page
import com.kserno.o2interview.ui.SnackbarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    MainScreen(
        title = viewModel.title,
        state = state,
        snackbarFlow = viewModel.snackbarFlow,
        loadingState = viewModel.loadingState,
        navController = navController,
    )

    ObserveEvents(eventFlow = viewModel.event) {
        when (it) {
            is Event.GoToActivation -> navController.navigate(AppNavGraph.Activation.toString())
            is Event.GoToScratch -> navController.navigate(AppNavGraph.ScratchCode.toString())
        }
    }
}

@Composable
private fun MainScreen(
    state: State,
    title: FormattedString,
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
            is State.Content -> MainScreenContent(state = state)
            is State.Loading -> LoadingScreen()
        }
    }
}

@Composable
private fun MainScreenContent(state: State.Content) {
    Card(state = state.cardState)
    Button(state = state.scratchButtonState)
    Button(state = state.activationButtonState)
}
