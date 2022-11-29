package types

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Int.pxToDp(): Dp {
    return with(LocalDensity.current) {
        this@pxToDp.toDp()
    }
}
