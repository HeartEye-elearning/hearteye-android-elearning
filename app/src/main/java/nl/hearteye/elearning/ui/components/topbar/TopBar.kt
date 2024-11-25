package nl.hearteye.elearning.ui.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.R
import nl.hearteye.elearning.ui.theme.BackgroundPrimary

@Composable
fun TopBar(
    showBackButton: Boolean,
    onBackButtonClick: () -> Unit
) {
    val statusBarHeight: Dp = with(LocalDensity.current) {
        WindowInsets.statusBars.getTop(LocalDensity.current).toDp()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + statusBarHeight)
            .background(color = BackgroundPrimary)
            .padding(top = statusBarHeight, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        } else {
            Spacer(modifier = Modifier.width(48.dp))
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.hearteye_logo),
                contentDescription = "HeartEye Logo",
                modifier = Modifier
                    .height(24.dp)
                    .width(161.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.width(48.dp))
    }
}
