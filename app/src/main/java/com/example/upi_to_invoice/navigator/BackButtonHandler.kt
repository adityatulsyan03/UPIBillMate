package com.example.upi_to_invoice.navigator

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLifecycleOwner

private val LocalBackPressedDispature =
    staticCompositionLocalOf<OnBackPressedDispatcherOwner?> {null}

private class ComposableBackNavigatorHandler(enabled: Boolean) : OnBackPressedCallback(enabled){
    lateinit var onBackPressed: () -> Unit

    override fun handleOnBackPressed() {
        onBackPressed()
    }

}

@Composable
internal fun ComposableHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val dispatcher = (LocalBackPressedDispature.current ?: return).onBackPressedDispatcher

    val handler = remember { ComposableBackNavigatorHandler(enabled) }

    DisposableEffect(dispatcher) {
        dispatcher.addCallback(handler)


        onDispose { handler.remove() }
    }

    LaunchedEffect(enabled) {
        handler.isEnabled = enabled
        handler.onBackPressed = onBackPressed
    }
}

@Composable
internal fun SystemBackButtonHandler(onBackPressed: () -> Unit) {
    CompositionLocalProvider(
        LocalBackPressedDispature provides LocalLifecycleOwner.current as ComponentActivity
    ) {
        ComposableHandler {
            onBackPressed()
        }
    }
}