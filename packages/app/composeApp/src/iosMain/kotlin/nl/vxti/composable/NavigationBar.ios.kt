package nl.vxti.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import nl.vxti.ViewController
import platform.UIKit.UITabBarController
import platform.UIKit.UITabBarControllerDelegateProtocol
import platform.UIKit.UITabBarItem
import platform.UIKit.UIViewController
import platform.UIKit.tabBarItem
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
internal actual fun NavigationBar(controller: ViewController) {
    val tabs by controller.tabs.collectAsState()
    val selectedTabIndex by controller.selectedTabIndex.collectAsState()
    val screen by controller.screen.collectAsState()

    // Fetch the initial screen and tabs once
    LaunchedEffect(Unit) {
        if (screen == null) {
            controller.fetchInitialScreen()
        }
    }

    // Only render the tab bar if there are tabs and a screen is loaded
    if (tabs.isNotEmpty() && screen != null) {
        val tabBarController = remember {
            UITabBarController().apply {
                this.delegate = object : NSObject(), UITabBarControllerDelegateProtocol {
                    override fun tabBarController(
                        tabBarController: UITabBarController,
                        didSelectViewController: UIViewController
                    ) {
                        controller.setSelectedTabIndex(tabBarController.selectedIndex.toInt())
                    }
                }
            }
        }

        UIKitViewController(
            factory = { tabBarController },
            modifier = Modifier,
            update = {
                // Sync selected tab index
                if (tabBarController.selectedIndex.toLong() != selectedTabIndex.toLong()) {
                    tabBarController.setSelectedIndex(selectedTabIndex.toULong())
                }

                // Sync the view controllers (tabs)
                val currentViewControllers = tabBarController.viewControllers?.mapNotNull { it as? UIViewController } ?: emptyList()
                if (currentViewControllers.size != tabs.size || currentViewControllers.map { it.title } != tabs.map { it.title }) {
                    val newViewControllers = tabs.mapIndexed { index, tab ->
                        ComposeUIViewController {
                            // A screen composable that observes the current screen for the tab
                            val currentScreenForTab by controller.screen.collectAsState()
                            if (currentScreenForTab?.id == tab.screenId) {
                                DynamicContentList(currentScreenForTab!!.content, controller)
                            }
                        }.apply {
                            this.title = tab.title
                            this.tabBarItem = UITabBarItem(
                                title = tab.title,
                                image = null, // TODO: Add UIImage for icon
                                tag = index.toLong()
                            )
                        }
                    }
                    tabBarController.setViewControllers(newViewControllers, animated = false)
                }
            }
        )
    }
}