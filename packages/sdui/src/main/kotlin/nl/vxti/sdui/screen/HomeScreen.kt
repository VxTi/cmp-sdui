package nl.vxti.sdui.screen

import nl.vxti.common.ButtonComponent
import nl.vxti.common.ButtonVariant
import nl.vxti.common.CurrencyType
import nl.vxti.common.ServerComponent
import nl.vxti.common.ImageComponent
import nl.vxti.common.ListItemContainer
import nl.vxti.common.NavigationEvent
import nl.vxti.common.ScrollableContainer
import nl.vxti.common.SearchBarComponent
import nl.vxti.common.SpacerComponent
import nl.vxti.common.TextComponent
import nl.vxti.common.TransactionListItem
import nl.vxti.core.AppRequestContext
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
