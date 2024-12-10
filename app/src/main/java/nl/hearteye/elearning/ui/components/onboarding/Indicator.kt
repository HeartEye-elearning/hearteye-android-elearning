package nl.hearteye.elearning.ui.components.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.theme.ForegroundGray
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(12.dp)
            .background(if (isSelected) ForegroundPrimary else ForegroundGray, shape = MaterialTheme.shapes.small)
    )
}