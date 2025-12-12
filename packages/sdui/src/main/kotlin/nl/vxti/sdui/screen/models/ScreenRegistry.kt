package nl.vxti.sdui.screen.models

import nl.vxti.common.screen.Screen
import nl.vxti.common.screen.ScreenTab
import nl.vxti.common.screen.models.ScreenRoute
import nl.vxti.core.AppRequestContext
import nl.vxti.sdui.screen.HomeScreen
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
            ScreenTab("Home", "https://www.vecteezy.com/vector-art/12528164-home-icon-house-icon-vector-illustration-perfect-for-all-project", ScreenRoute.HOME),
            ScreenTab("Search", "search.png", ScreenRoute.SEARCH),
            ScreenTab("Profile", "profile.png", ScreenRoute.PROFILE),
            ScreenTab("Settings", "settings.png", ScreenRoute.SETTINGS)
        )

        const val DEFAULT_SCREEN_IDENTIFIER = HomeScreen.Companion.SCREEN_IDENTIFIER;
    }
}