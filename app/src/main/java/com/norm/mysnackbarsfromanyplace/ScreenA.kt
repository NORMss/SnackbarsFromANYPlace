package com.norm.mysnackbarsfromanyplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun ScreenA(
    onNavigateB: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScreenAViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                viewModel.showSnakbar()
            }
        ) {
            Text(
                text = "Show Snackbar with action"
            )
        }
        Button(
            onClick = {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = "Hello, Android!"
                        )
                    )
                }
            }
        ) {
            Text(
                text = "Show Snackbar"
            )
        }
        Button(
            onClick = onNavigateB
        ) {
            Text(
                text = "Navigate to screen B"
            )
        }
    }
}