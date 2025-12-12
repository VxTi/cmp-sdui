package nl.vxti.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.focused
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import nl.vxti.ViewController
import nl.vxti.common.SearchBarComponent
import nl.vxti.common.core.Locale
import nl.vxti.core.AppInstance
import nl.vxti.core.requestFocusAsync
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SearchBarDrawable(component: SearchBarComponent, controller: ViewController) {
    var text by remember { mutableStateOf("") }
    var focussed by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { requestFocusAsync(focusRequester) }

    Box(
        modifier = Modifier
            .sizeIn(minHeight = 60.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFD8D8D8))
            .clearAndSetSemantics { }
            .clickable { focussed = true }
            .semantics { focused = focussed }
            .focusRequester(focusRequester)
            .onFocusChanged { focussed = it.isFocused }
            .focusable(),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                BasicTextField(
                    lineLimits = TextFieldLineLimits.SingleLine,
                    cursorBrush = SolidColor(Color.Black),
                    modifier = Modifier
                        .padding(bottom = 1.dp)
                        .fillMaxWidth()
                        .onFocusChanged {
                            focussed = it.isFocused
                        },
                    state = TextFieldState(text),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    val controller = ViewController(AppInstance(1, Locale.NL_NL))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

    SearchBarDrawable(
        SearchBarComponent("id", "test"),
        controller
    )
    }
}