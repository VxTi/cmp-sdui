package nl.vxti.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import nl.vxti.common.screen.Screen

@Composable
internal fun ServerDrivenScreen(screen: Screen, controller: NavHostController) {
    DynamicContentList(screen.content, controller)
}
