package nl.vxti.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import nl.vxti.common.components.container.ListItemContainer

@Composable
internal fun ListItemContainerDrawable(
    component: ListItemContainer,
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
            /*component.items.forEachIndexed { index, item ->
                ListItemDrawable(item)

                if (index < component.items.filterIsInstance<TransactionListItem>().size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(1.dp)
                            .background(MaterialTheme.colorScheme.surfaceDim)
                    )
                }
            }*/
        }
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(4.dp)
        )
    }
}