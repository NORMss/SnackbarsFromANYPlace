package com.norm.mysnackbarsfromanyplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.norm.mysnackbarsfromanyplace.ui.theme.MySnackbarsFromANYPlaceTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object ScreenA

@Serializable
data object ScreenB

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MySnackbarsFromANYPlaceTheme {
                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                val scope = rememberCoroutineScope()
                ObserveAsEvents(
                    flow = SnackbarController.events,
                    key1 = snackbarHostState,
                ) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()

                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Long,
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = ScreenA,
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        composable<ScreenA> {
                            ScreenA(
                                modifier = Modifier
                                    .fillMaxSize(),
                                onNavigateB = {
                                    navController.navigate(ScreenB)
                                }
                            )
                        }
                        composable<ScreenB> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    "This ScreenB"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}