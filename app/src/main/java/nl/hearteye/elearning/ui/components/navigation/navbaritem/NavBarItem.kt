package nl.hearteye.elearning.ui.components.navigation.navbaritem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun NavBarItem(
    iconRes: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .width(IntrinsicSize.Min)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = if (selected) ForegroundPrimary.copy(alpha = 0.3f) else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .width(43.dp)
                .height(28.dp)
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = iconRes,
                    contentDescription = label,
                    tint = ForegroundPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = label,
            style = if (selected) typography.bodySmall.copy(fontWeight = FontWeight.Bold) else typography.bodySmall,
            color = ForegroundPrimary
        )
    }
}



