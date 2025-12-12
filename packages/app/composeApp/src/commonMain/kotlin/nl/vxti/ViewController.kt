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
import androidx.navigation.compose.rememberNavController
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
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    currentScreen?.let { screen ->
                        ServerDrivenScreen(screen, navHostController)
                    } ?: EmptyScreenFallback(
                        isRefreshing = isLoading, // Pass the state down
                        onRefresh = { navigationController.refreshScreen() } // Pass the event up
                    )
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