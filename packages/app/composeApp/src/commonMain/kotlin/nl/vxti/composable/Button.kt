package nl.vxti.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.vxti.ViewController
import nl.vxti.common.ButtonComponent
import nl.vxti.common.ButtonVariant
import nl.vxti.common.core.Locale
import nl.vxti.core.AppInstance
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ButtonDrawable(component: ButtonComponent, controller: ViewController) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundColor = if (isPressed) Color.DarkGray else Color.Black

    Button(
        onClick = { controller.emitEvents(component.interactionEvents); },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        interactionSource = interactionSource,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = component.text,
            color = MaterialTheme.colorScheme.inversePrimary)
    }
}

@Preview
@Composable
fun ButtonPreview() {
    ButtonDrawable(
        ButtonComponent("Hello world", ButtonVariant.NORMAL, emptyList(), "test-button"),
        ViewController(AppInstance(1, Locale.NL_NL))
    )
}