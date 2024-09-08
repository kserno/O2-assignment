package com.kserno.o2interview.ui

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kserno.o2interview.R

@Immutable
data class CardState(
    @ColorRes val backgroundColor: Int,
    val text: FormattedString?,
)

@Composable
fun Card(state: CardState) {
    Box(
        modifier = Modifier
            .width(240.dp)
            .height(100.dp)
            .background(
                color = Color(LocalContext.current.getColor(state.backgroundColor)),
                shape = RoundedCornerShape(10.dp),
            ),

    ) {
        state.text?.let {
            Text(text = state.text.asString())
        }
    }
}

@Preview
@Composable
fun Card_Preview() {
    PreviewTheme {
        Card(state = CardState(backgroundColor = R.color.yellow, text = "Test card".asPlainString()))
    }
}
