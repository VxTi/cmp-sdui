package me.vxti

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.vxti.common.Event
import me.vxti.common.NavigationEvent
import me.vxti.common.ScreenResponse
import me.vxti.common.screen.Screen
import me.vxti.common.screen.ScreenTab
import me.vxti.core.AppInstance
import me.vxti.core.CacheSet
import me.vxti.core.ServerConnector

internal class ViewController(
    val appInstance: AppInstance = AppInstance.fromConfig(),
    val serverConnector: ServerConnector = ServerConnector(appInstance)
) {

    val navigationStack = NavigationStack();

    private val screenCache = CacheSet<Screen>();

    // Internal mutable StateFlows
    private val _loadingState = MutableStateFlow(false);
    private val _tabs = MutableStateFlow<List<ScreenTab>>(emptyList())
    private val _currentTabIndex = MutableStateFlow(0);
    private val _currentScreen = MutableStateFlow<Screen?>(null);

    // Exposed as read-only StateFlows
    val tabs: StateFlow<List<ScreenTab>> = _tabs.asStateFlow()
    val screen: StateFlow<Screen?> = _currentScreen.asStateFlow();
    val selectedTabIndex: StateFlow<Int> = _currentTabIndex.asStateFlow();
    val screenStateBusy: StateFlow<Boolean> = _loadingState.asStateFlow();

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    fun emitEvents(events: List<Event>) = events.forEach { emitEvent(it) }

    fun emitEvent(event: Event) {
        scope.launch {
            when (event) {
                is NavigationEvent -> {
                    println("Navigation action invoked to path: ${event.screenId}");

                    screenCache.get(event.screenId)
                        ?.let { cachedScreen -> setCurrentScreen(cachedScreen) } ?: fetchScreen(
                        event.screenId,
                        NavigationIntent.PUSH_AND_CACHE
                    )
                }
            }
        }
    }

    /**
     * Preprocesses events. This can mean caching additional data before it's being used,
     * or ensuring linked data is present.
     */
    fun preprocessEvents(events: List<Event>) {
        events.forEach { event -> preprocessEvent(event) }
    }

    fun preprocessEvent(event: Event) {
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
        fetchScreen(screenId, NavigationIntent.CACHE_SCREEN);
    }

    fun refreshScreen() {
        val currentScreenId = screen.value?.id ?: return;

        fetchScreen(currentScreenId, NavigationIntent.PUSH_AND_CACHE);
    }

    fun fetchInitialScreen() {
        scope.launch {
            runLoadableAction {
                val response = serverConnector.fetch<ScreenResponse>(appInstance, "/")
                _tabs.value = response?.tabs ?: emptyList();

                val initialScreen = response?.screen ?: return@runLoadableAction;

                navigationStack.push(initialScreen)
            }
        }
    }

    /**
     * Retrieves a screen from the server, and optionally caches it.
     */
    fun fetchScreen(screenIdentifier: String, navigationIntent: NavigationIntent) {
        scope.launch {
            serverConnector.fetchScreen(screenIdentifier) { screenResponse ->
                println("Fetched screen with id: ${screenResponse?.screen?.id}")
                val screen: Screen = screenResponse?.screen ?: return@fetchScreen;

                val isCurrent = screen.id == _currentScreen.value?.id

                val canCache =
                    (navigationIntent.mask and NavigationIntent.CACHE_SCREEN.mask) != 0 && !isCurrent

                _currentScreen.value = screen

                if (canCache) screenCache.put(
                    key = screen.id,
                    data = screen,
                    expiresIn = screen.cacheDurationMs
                );

                val shouldPushToStack =
                    (navigationIntent.mask and NavigationIntent.PUSH_TO_STACK.mask) != 0 && !isCurrent

                val shouldReplaceInStack =
                    (navigationIntent.mask and NavigationIntent.REPLACE_IN_STACK.mask) != 0 && !isCurrent

                println("Navigation stack action - push: $shouldPushToStack, replace: $shouldReplaceInStack")

                if (shouldPushToStack) {
                    navigationStack.push(screen)
                } else if (shouldReplaceInStack) {
                    navigationStack.pruneAndPush(screen)
                }
            }
        }
    }

    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen;

        val tabIndex = tabs.value.indexOfFirst { tab -> tab.screenId == screen.id };

        if (tabIndex > -1) setSelectedTabIndex(tabIndex)
    }

    private suspend fun runLoadableAction(action: suspend () -> Unit) {
        _loadingState.value = true;
        try {
            action()
        } catch (e: Exception) {
            println("An error occurred whilst attempting to execute action: ${e.message}")
        } finally {
            _loadingState.value = false;
        }
    }

    fun pushToNavigationStack(screen: Screen) {
        _currentScreen.value = screen;
    }

    /**
     * Sets the selected tab index and updates the screen if it's cached.
     */
    fun setSelectedTabIndex(index: Int) {
        _currentTabIndex.value = index;

        screenCache.get(tabs.value[index].screenId)?.let { screen ->
            _currentScreen.value = screen
            navigationStack.push(screen)
            return;
        }

        scope.launch {
            fetchScreen(tabs.value[index].screenId, NavigationIntent.REPLACE_AND_CACHE)
        }
    }

    fun getScreenById(screenId: String): Screen? {
        val existing = screenCache.get(screenId);
        if (existing != null) return existing;

        fetchScreen(screenId, NavigationIntent.PUSH_AND_CACHE);
        return null;
    }
}

enum class NavigationIntent(val mask: Int) {
    PUSH_TO_STACK(1),
    REPLACE_IN_STACK(2),
    CACHE_SCREEN(4),
    PUSH_AND_CACHE(1 or 4),
    REPLACE_AND_CACHE(2 or 4)
}

