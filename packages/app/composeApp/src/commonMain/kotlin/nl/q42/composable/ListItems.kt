package me.vxti.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import me.vxti.ViewController
import androidx.compose.ui.unit.dp
import me.vxti.common.ListItemContainer
import me.vxti.common.TransactionListItem
import me.vxti.common.core.Locale
import me.vxti.core.AppInstance
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import me.vxti.common.CurrencyType
import me.vxti.common.ListItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ListItemContainerDrawable(
    component: ListItemContainer,
    controller: ViewController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (component.title != null) {
            Text(
                text = component.title!!,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceDim),
                    MaterialTheme.shapes.medium
                )

        ) {
            component.items.forEachIndexed { index, item ->
                ListItemDrawable(item, controller)
                if (index < component.items.filterIsInstance<TransactionListItem>().size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(1.dp)
                            .background(MaterialTheme.colorScheme.surfaceDim)
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(4.dp)
        )
    }
}

@Composable
internal fun ListItemDrawable(item: ListItem, controller: ViewController) {
    when (item) {
        is TransactionListItem -> TransactionListItemDrawable(item, controller)
    }
}

@Composable
internal fun TransactionListItemDrawable(item: TransactionListItem, controller: ViewController) {
    Button(
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 64.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        colors = ButtonDefaults.textButtonColors(),
        onClick = { controller.emitEvents(item.interactionEvents) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = item.merchantThumbnailUrl,
                    contentDescription = item.merchantName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    item.merchantName,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                item.amount,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun ListItemContainerPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    {
        ListItemContainerDrawable(
            ListItemContainer(
                listOf(
                    TransactionListItem(
                        "Transaction 1",
                        "Description 1",
                        "$10.00",
                        CurrencyType.USD,
                        "https://cdn-icons-png.flaticon.com/512/25/25069.png",
                        itemId = "item-1"
                    ),
                    TransactionListItem(
                        "Transaction 2",
                        "Description 2",
                        "$20.00",
                        CurrencyType.USD,
                        "https://cdn-icons-png.flaticon.com/512/25/25069.png",
                        itemId = "item-2"
                    ),
                    TransactionListItem(
                        "Transaction 3",
                        "Description 3",
                        "$30.00",
                        CurrencyType.USD,
                        "https://cdn-icons-png.flaticon.com/512/25/25069.png",
                        itemId = "item-3"
                    ),
                ),
                "Recent Transactions",
                "test-list-item-container"
            ),
            ViewController(AppInstance(1, Locale.NL_NL))
        )
    }
}

@Preview
@Composable
fun PreviewTransactionListItem() {
    Box(modifier = Modifier.fillMaxSize())
    TransactionListItemDrawable(
        TransactionListItem(
            "Transaction 1",
            "Description 1",
            "$10.00",
            me.vxti.common.CurrencyType.USD,
            "https://cdn-icons-png.flaticon.com/512/25/25069.png",
            itemId = "item-1",
        ),
        ViewController(AppInstance(1, Locale.NL_NL))
    )
}