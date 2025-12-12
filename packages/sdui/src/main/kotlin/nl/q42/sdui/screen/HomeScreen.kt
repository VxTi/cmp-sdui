package me.vxti.sdui.screen

import me.vxti.common.ButtonComponent
import me.vxti.common.ButtonVariant
import me.vxti.common.CurrencyType
import me.vxti.common.ServerComponent
import me.vxti.common.ImageComponent
import me.vxti.common.ListItemContainer
import me.vxti.common.NavigationEvent
import me.vxti.common.ScrollableContainer
import me.vxti.common.SearchBarComponent
import me.vxti.common.SpacerComponent
import me.vxti.common.TextComponent
import me.vxti.common.TransactionListItem
import me.vxti.core.AppRequestContext
import org.springframework.stereotype.Component

@Component
class HomeScreen : ScreenInstance {

    override fun content(context: AppRequestContext): List<ServerComponent> {
        return listOf(
            SearchBarComponent(placeholder = "Search...", contentId = "search-2"),
            SpacerComponent(size = 3, contentId = "spacer-2"),
            ListItemContainer(
                listOf(
                    TransactionListItem(
                        "Transaction 1",
                        "Description 1",
                        "-$10.00",
                        CurrencyType.USD,
                        "https://mediamarkt.nl/public/manifest/favicon-Media-48x48.png",
                        listOf(
                            NavigationEvent("search")
                        ),
                        itemId = "item-1"
                    ),
                    TransactionListItem(
                        "Transaction 2",
                        "Description 2",
                        "-$10.00",
                        CurrencyType.USD,
                        "https://mediamarkt.nl/public/manifest/favicon-Media-48x48.png",
                        itemId = "item-2"
                    ),
                    TransactionListItem(
                        "Transaction 3",
                        "Description 3",
                        "-$10.00",
                        CurrencyType.USD,
                        "https://mediamarkt.nl/public/manifest/favicon-Media-48x48.png",
                        itemId = "item-3"
                    ),
                ),
                "Recent Transactions",
                "test-list-item-container"
            ),
            ButtonComponent(
                "Hello world from SDUI",
                ButtonVariant.NORMAL,
                listOf(NavigationEvent("profile")),
                "test-button"
            ),
        )
    }

    override fun name(): String {
        return SCREEN_IDENTIFIER
    }

    companion object {
        const val SCREEN_IDENTIFIER: String = "home"
    }
}
