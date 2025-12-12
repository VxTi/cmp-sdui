package me.vxti.core

import androidx.compose.ui.focus.FocusRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.job

fun CoroutineScope.requestFocusAsync(focusRequester: FocusRequester) {
    this.coroutineContext.job.invokeOnCompletion {
        try {
            focusRequester.requestFocus()
        } catch (e: Throwable) {
            error("Failed to request focus for search input header")
        }
    }
}
