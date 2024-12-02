package nl.hearteye.elearning.ui.components.quiz.answerbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.theme.ForegroundGray
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun AnswerBar(
    id: String,
    answer: String,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .height(50.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onCheckedChange(!isSelected)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = answer,
            color = if (isSelected) Color.Black else ForegroundGray,
            style = typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF5F5F5))
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Transparent,
                    checkmarkColor = ForegroundPrimary,
                    uncheckedColor = Color.Transparent
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}



