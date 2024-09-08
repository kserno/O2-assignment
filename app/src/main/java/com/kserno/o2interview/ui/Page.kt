package com.kserno.o2interview.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page(
    title: FormattedString,
    navController: NavController,
    snackbarFlow: Flow<SnackbarEvent>,
    loadingState: StateFlow<Boolean>,
    content: @Composable ColumnScope.() -> Unit,
) {
    val loading by loadingState.collectAsState()
    val errorSnackbarHost = remember { SnackbarHostState() }
    val infoSnackbarHost = remember { SnackbarHostState() }
    val successSnackbarHost = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = title.asString())
            }, navigationIcon = {
                if (navController.previousBackStackEntry != null) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                        )
                    }
                }
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = errorSnackbarHost) {
                Snackbar(it, containerColor = MaterialTheme.colorScheme.errorContainer)
            }
            SnackbarHost(hostState = infoSnackbarHost) {
                Snackbar(it, containerColor = Color.Gray)
            }
            SnackbarHost(hostState = successSnackbarHost) {
                Snackbar(it, containerColor = Color.Green)
            }
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            if (loading) {
                LoadingProgressiveIndicator()
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(32.dp))
                content()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        snackbarFlow.collectLatest {
            when (it) {
                is SnackbarEvent.ErrorSnackbar -> errorSnackbarHost.showSnackbar(message = it.message)
                is SnackbarEvent.InfoSnackbar -> infoSnackbarHost.showSnackbar(message = it.message)
                is SnackbarEvent.SuccessSnackbar -> successSnackbarHost.showSnackbar(message = it.message)
            }
        }
    }
}

@Composable
fun <T> ObserveEvents(eventFlow: Flow<T>, onEvent: (T) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        eventFlow.collectLatest {
            onEvent(it)
        }
    }
}

@Composable
private fun LoadingProgressiveIndicator() {
    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
}
