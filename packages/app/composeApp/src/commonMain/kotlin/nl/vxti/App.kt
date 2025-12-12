package nl.vxti

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.vxti.composable.NavigationBar
import nl.vxti.composable.ServerDrivenScreen
import nl.vxti.composable.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
internal fun App(viewController: ViewController) = AppTheme {

    val isLoading by viewController.screenStateBusy.collectAsStateWithLifecycle()

    val currentScreen by remember(viewController.navigationStack) {
        derivedStateOf { viewController.navigationStack.currentScreen() }
    }

    LaunchedEffect(Unit) { viewController.fetchInitialScreen(); }

    Scaffold(
        bottomBar = { NavigationBar(viewController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else currentScreen?.let { screen ->
                    ServerDrivenScreen(screen, viewController)
                } ?: Text(
                    "No screen data available.",
                    modifier = Modifier.padding(vertical = 16.dp)
                )

            }
        }
    }
}
