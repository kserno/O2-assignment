package com.kserno.o2interview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kserno.o2interview.activation.ui.ActivationScreen
import com.kserno.o2interview.main.ui.MainScreen
import com.kserno.o2interview.scratch.ui.ScratchScreen
import com.kserno.o2interview.ui.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = AppNavGraph.Main.toString()) {
                    composable(AppNavGraph.Main.toString()) {
                        MainScreen(navController)
                    }
                    composable(AppNavGraph.ScratchCode.toString()) {
                        ScratchScreen(navController)
                    }
                    composable(AppNavGraph.Activation.toString()) {
                        ActivationScreen(navController)
                    }
                }
            }
        }
    }
}

object AppNavGraph {
    data object Main
    data object ScratchCode
    data object Activation
}
