package nl.vxti

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.vxti.common.screen.Screen
import nl.vxti.composable.ServerDrivenScreen
import nl.vxti.core.NavigationController
import nl.vxti.core.refreshScreen
import nl.vxti.navigation.TabNavigation

@Composable
internal fun ViewController() {
    val navHostController = rememberNavController()
    val navigationController = remember { NavigationController(navHostController) }

    val isLoading by navigationController.screenStateBusy.collectAsState()
    val currentScreen by navigationController.screen.collectAsState()

    Scaffold(
        bottomBar = { TabNavigation(navHostController, navigationController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppContent(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    navHostController = navHostController,
                    isLoading = isLoading,
                    currentScreen = currentScreen,
                    onRefresh = { navigationController.refreshScreen() }
                )
            }
        }
    }
}

/**
 * Determines what content to display based on the current SDUI state.
 * It now contains the NavHost to correctly initialize the NavHostController.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    isLoading: Boolean,
    currentScreen: Screen?,
    onRefresh: () -> Unit
) {
    // The NavHost is essential for initializing the navHostController used by TabNavigation.
    NavHost(navController = navHostController, startDestination = "/") {
        composable("/") {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // This 'when' block now lives inside the single NavHost destination
                when {
                    // If we have a screen, show it with pull-to-refresh
                    currentScreen != null -> {
                        PullToRefreshBox(isRefreshing = isLoading, onRefresh = onRefresh) {
                            ServerDrivenScreen(currentScreen, navHostController)
                        }
                    }
                    // While loading the very first screen
                    isLoading -> {
                        CircularProgressIndicator()
                    }
                    // If no screen is available and not loading (e.g., network error)
                    else -> {
                        EmptyScreenFallback(
                            isRefreshing = isLoading,
                            onRefresh = onRefresh
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EmptyScreenFallback(
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(1) {
                ListItem({
                    Text(
                        "No screen data available.",
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                })
            }
        }
    }
}