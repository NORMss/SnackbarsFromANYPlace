package com.norm.mysnackbarsfromanyplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ScreenAViewModel : ViewModel() {
    fun showSnakbar() {
        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = "Hello from ViewModel",
                    action = SnackbarAction(
                        name = "Ckick me pls!",
                        action = {
                            SnackbarController.sendEvent(
                                event = SnackbarEvent(
                                    message = "Action pressed!"
                                )
                            )
                        }
                    )
                )
            )
        }
    }
}