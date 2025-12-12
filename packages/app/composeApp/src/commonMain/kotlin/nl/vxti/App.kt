package nl.vxti

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import nl.vxti.composable.theme.AppTheme
import nl.vxti.core.EventBus
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
internal fun App() = AppTheme {
    LaunchedEffect(Unit) {
        EventBus.events.collect { event ->
            println("Received event: $event")
        }
    }
    ViewController()
}
