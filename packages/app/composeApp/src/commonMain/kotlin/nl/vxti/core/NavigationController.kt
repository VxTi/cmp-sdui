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

    val _loadingState = MutableStateFlow(false)
    val _tabs = MutableStateFlow<List<ScreenTab>>(emptyList())
    val _currentScreen = MutableStateFlow<Screen?>(null)

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


    fun setCurrentScreen(
        screen: Screen,
        navigationAction: ScreenNavigationAction = ScreenNavigationAction.REPLACE
    ) {
        val shouldReplaceInStack =
            (navigationAction.mask and ScreenNavigationAction.REPLACE.mask) != 0

        val options = if (shouldReplaceInStack) {
            navOptions {
                popUpTo(navHostController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else navOptions {}

        _currentScreen.value = screen;
        navHostController.navigate(screen.id, options)
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
    suspendScreenState {
        serverConnector.fetchInitialScreen { screenResponse ->
            val screen: Screen = screenResponse?.screen ?: return@fetchInitialScreen;


            screenCache.put(
                key = screen.id,
                data = screen,
                expiresIn = screen.cacheDurationMs
            )

            println("Received screen tabs: ${screenResponse.tabs}")

            _tabs.value = screenResponse.tabs ?: _tabs.value

            println("new tabs: ${_tabs.value}, received: ${screenResponse.tabs}")
            setCurrentScreen(screen)
        }
    }
}

/**
 * Wraps the execution of a suspending action while managing the screen's loading state.
 * The loading state will be set to `true` before the action is executed and reverted
 * to `false` regardless of success or failure of the action.
 */
fun NavigationController.suspendScreenState(action: suspend () -> Unit) {
    scope.launch {
        _loadingState.value = true
        println("Temporarily suspending screen retrieval")
        try {
            action()
        } catch (e: Exception) {
            println("An error occurred whilst attempting to execute action: ${e.message}")
        }
        println("Resuming screen retrieval")
        _loadingState.value = false;
    }
}

fun NavigationController.refreshScreen() {
    screen.value?.id?.let {
        fetchScreen(it, ScreenNavigationAction.REPLACE_AND_STORE);
    } ?: fetchInitialScreen()
}


/**
 * Retrieves a screen from the server, and optionally caches it.
 */
fun NavigationController.fetchScreen(
    screenIdentifier: String,
    navigationAction: ScreenNavigationAction
) {
    suspendScreenState {
        serverConnector.fetchScreen(screenIdentifier) { screenResponse ->
            val screen: Screen = screenResponse?.screen ?: return@fetchScreen;
            val isCurrent = screen.id == _currentScreen.value?.id

            val shouldCacheScreen =
                (navigationAction.mask and ScreenNavigationAction.STORE.mask) != 0 && !isCurrent

            if (shouldCacheScreen) {
                screenCache.put(
                    key = screen.id,
                    data = screen,
                    expiresIn = screen.cacheDurationMs
                )
            }

            val requiresScreenUpdate =
                navigationAction.mask and (
                        ScreenNavigationAction.REPLACE.mask or
                                ScreenNavigationAction.REPLACE_AND_STORE.mask
                        ) != 0

            if (!requiresScreenUpdate) return@fetchScreen

            setCurrentScreen(screen, navigationAction)
        }
    }
}