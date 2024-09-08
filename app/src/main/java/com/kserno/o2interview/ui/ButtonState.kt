package com.kserno.o2interview.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Immutable
data class ButtonState(
    val text: FormattedString,
    val enabled: Boolean = true,
    val onClick: () -> Unit,
)

@Composable
fun Button(state: ButtonState, modifier: Modifier = Modifier) {
    androidx.compose.material3.Button(
        onClick = state.onClick,
        modifier = modifier,
        enabled = state.enabled,
    ) {
        Text(text = state.text.asString())
    }
}

@Preview
@Composable
fun Button_Preview() {
    Button(
        state = ButtonState(
            text = "Test button".asPlainString(),
            enabled = true,
            onClick = {},
        ),
    )
}

@Preview
@Composable
fun ButtonDisabled_Preview() {
    Button(
        state = ButtonState(
            text = "Test button".asPlainString(),
            enabled = false,
            onClick = {},
        ),
    )
}
