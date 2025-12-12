package me.vxti.sdui

import me.vxti.common.screen.Screen
import me.vxti.common.screen.ScreenTab
import me.vxti.core.AppRequestContext
import me.vxti.sdui.screen.*
import org.springframework.stereotype.Service

@Service
class ScreenRegistry(private val screens: MutableList<ScreenInstance>) {

    val all: MutableList<ScreenInstance>
        get() = this.screens

    fun getByIdentifier(screenIdentifier: String, context: AppRequestContext): Screen? {
        return this.screens.stream()
            .filter { screen: ScreenInstance? -> screen?.name().equals(screenIdentifier) }
            .findFirst()
            .map { screen: ScreenInstance -> screen.createScreen(context) }
            .orElse(null)
    }

    fun defaultScreen(context: AppRequestContext): Screen {
        val initial = getByIdentifier(DEFAULT_SCREEN_IDENTIFIER, context)

        return when {
            initial != null -> initial
            screens.isNotEmpty() -> screens[0].createScreen(context)
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
