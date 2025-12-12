package nl.vxti.sdui

import nl.vxti.common.screen.Screen
import nl.vxti.common.screen.ScreenTab
import nl.vxti.core.AppRequestContext
import nl.vxti.sdui.screen.*
import nl.vxti.sdui.screen.models.IScreen
import org.springframework.stereotype.Service

@Service
class ScreenRegistry(private val screens: MutableList<IScreen>) {

    val all: MutableList<IScreen>
        get() = this.screens

    fun getByIdentifier(screenIdentifier: String, context: AppRequestContext): Screen? {
        return this.screens.stream()
            .filter { screen: IScreen? -> screen?.name().equals(screenIdentifier) }
            .findFirst()
            .map { screen: IScreen -> screen.create(context) }
            .orElse(null)
    }

    fun defaultScreen(context: AppRequestContext): Screen {
        val initial = getByIdentifier(DEFAULT_SCREEN_IDENTIFIER, context)

        return when {
            initial != null -> initial
            screens.isNotEmpty() -> screens[0].create(context)
            else -> throw IllegalStateException("No screens available in the registry")
        }

    }

    companion object {
        val SCREEN_TABS: List<ScreenTab> = listOf<ScreenTab>(
            ScreenTab("Home", "home.png", HomeScreen.SCREEN_IDENTIFIER),
            ScreenTab("Search", "search.png", SearchScreen.SCREEN_IDENTIFIER),
            ScreenTab("Profile", "profile.png", ProfileScreen.SCREEN_IDENTIFIER),
            ScreenTab("Settings", "settings.png", SettingsScreen.SCREEN_IDENTIFIER)
        )

        const val DEFAULT_SCREEN_IDENTIFIER = HomeScreen.SCREEN_IDENTIFIER;
    }
}
