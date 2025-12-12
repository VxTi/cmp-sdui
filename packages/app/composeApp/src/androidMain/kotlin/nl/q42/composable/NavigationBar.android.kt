package me.vxti.composable

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.vxti.ViewController

@Composable
internal actual fun NavigationBar(controller: ViewController) {

    val tabs by controller.tabs.collectAsState();
    val selectedTabIndex by controller.selectedTabIndex.collectAsState();

    NavigationBar {
        tabs.forEachIndexed { index, tab ->
            NavigationBarItem(
                icon = { },
                label = { Text(tab.title) },
                selected = selectedTabIndex == index,
                onClick = {
                    if (selectedTabIndex != index) {
                        controller.setSelectedTabIndex(index);
                    }
                }
            )
        }
    }
}