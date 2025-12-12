package nl.vxti.core

import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.vxti.common.events.NavigationEvent
import nl.vxti.common.events.models.IEvent
import nl.vxti.common.screen.Screen
import nl.vxti.common.screen.ScreenTab

class NavigationController(
    val navHostController: NavHostController
) {
    val appInstance: AppInstance = AppInstance.fromConfig()
    val serverConnector = ServerConnector(appInstance)
    val screenCache = CacheSet<Screen>()

    // Internal mutable StateFlows
    val _loadingState = MutableStateFlow(false)
    val _tabs = MutableStateFlow<List<ScreenTab>>(emptyList())
    val _currentTabIndex = MutableStateFlow(0)
    val _currentScreen = MutableStateFlow<Screen?>(null)

    // Exposed as read-only StateFlows
    val tabs: StateFlow<List<ScreenTab>> = _tabs.asStateFlow()
    val screen: StateFlow<Screen?> = _currentScreen.asStateFlow()
    val screenStateBusy: StateFlow<Boolean> = _loadingState.asStateFlow()

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        fetchInitialScreen()
    }

    private fun emitEvents(events: List<IEvent>) = events.forEach { emitEvent(it) }

    private fun emitEvent(event: IEvent) {
        scope.launch {
            when (event) {
                is NavigationEvent -> {
                    println("Navigation action invoked to path: ${event.screenId}");

                    screenCache.get(event.screenId)
                        ?.let { cachedScreen -> setCurrentScreen(cachedScreen) } ?: fetchScreen(
                        event.screenId,
                        ScreenNavigationAction.PUSH_AND_STORE
                    )
                }
            }
        }
    }

    /**
     * Preprocesses events. This can mean caching additional data before it's being used,
     * or ensuring linked data is present.
     */
    private fun preprocessEvents(events: List<IEvent>) {
        events.forEach { event -> preprocessEvent(event) }
    }

    private fun preprocessEvent(event: IEvent) {
        when (event) {
            is NavigationEvent -> {
                if (!event.prefetch) return;

                tryPrefetchScreen(event.screenId);
            }
        }
    }

    fun tryPrefetchScreen(screenId: String) {
        println("Prefetching screen");

        screenCache.get(screenId) ?: return;
        fetchScreen(screenId, ScreenNavigationAction.STORE);
    }


    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen;

        navHostController.navigate(screen.id)
    }

    fun getScreenById(screenId: String): Screen? {
        val existing = screenCache.get(screenId);
        if (existing != null) return existing;

        fetchScreen(screenId, ScreenNavigationAction.PUSH_AND_STORE);
        return null;
    }
}

/**
 * Fetches the initial screen from the server and sets it as the current screen.
 * This is typically the first screen the user sees.
 */
fun NavigationController.fetchInitialScreen() {
    fetchScreen("/", ScreenNavigationAction.REPLACE_AND_STORE);
}

/**
 * Wraps the execution of a suspending action while managing the screen's loading state.
 * The loading state will be set to `true` before the action is executed and reverted
 * to `false` regardless of success or failure of the action.
 */
fun NavigationController.suspendScreenState(action: suspend () -> Unit) {
    scope.launch {
        _loadingState.value = true;
        try {
            action()
        } catch (e: Exception) {
            println("An error occurred whilst attempting to execute action: ${e.message}")
        } finally {
            _loadingState.value = false;
        }
    }
}

fun NavigationController.refreshScreen() {
    val currentScreenId = screen.value?.id ?: return;

    fetchScreen(currentScreenId, ScreenNavigationAction.REPLACE_AND_STORE);
}


/**
 * Retrieves a screen from the server, and optionally caches it.
 */
fun NavigationController.fetchScreen(
    screenIdentifier: String,
    screenNavigationAction: ScreenNavigationAction
) {
    suspendScreenState {
        serverConnector.fetchScreen(screenIdentifier) { screenResponse ->
            val screen: Screen = screenResponse?.screen ?: return@fetchScreen;
            val isCurrent = screen.id == _currentScreen.value?.id

            val canCache =
                (screenNavigationAction.mask and ScreenNavigationAction.STORE.mask) != 0 && !isCurrent

            _currentScreen.value = screen

            if (canCache) {
                screenCache.put(
                    key = screen.id,
                    data = screen,
                    expiresIn = screen.cacheDurationMs
                )
            }

            val shouldPushToStack =
                (screenNavigationAction.mask and ScreenNavigationAction.PUSH.mask) != 0 && !isCurrent

            val shouldReplaceInStack =
                (screenNavigationAction.mask and ScreenNavigationAction.REPLACE.mask) != 0 && !isCurrent

            println("Navigation stack action - push: $shouldPushToStack, replace: $shouldReplaceInStack")


            _tabs.value = screenResponse.tabs ?: _tabs.value

            if (!shouldPushToStack && !shouldReplaceInStack) {
                return@fetchScreen
            }

            setCurrentScreen(screen)
            val options = if (shouldReplaceInStack) {
                navOptions {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else navOptions {}

            navHostController.navigate(screen.id, options)
        }
    }
}