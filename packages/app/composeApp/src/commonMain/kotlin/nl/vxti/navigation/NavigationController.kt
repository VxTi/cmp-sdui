package nl.vxti.navigation


import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import nl.vxti.common.screen.ScreenTab
import nl.vxti.core.NavigationController

@Composable
fun TabNavigation(
    navController: NavHostController,
    navigationController: NavigationController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        navigationController.tabs.value.forEach { bottomBarItem: ScreenTab ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(bottomBarItem.screenId::class)
            } == true

            BottomNavigationItem(
                tab = bottomBarItem,
                navController = navController,
                selected = isSelected
            )
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    tab: ScreenTab,
    navController: NavHostController,
    selected: Boolean
) {
    NavigationBarItem(
        icon = {
            tab.imageUrl?.let {
                AsyncImage(
                    model = it,
                    modifier = Modifier.size(24.dp),
                    contentDescription = tab.title
                )
            }
        },
        selected = selected,
        onClick = {
            navController.navigate(tab.screenId) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        label = { Text(tab.title) }
    )
}