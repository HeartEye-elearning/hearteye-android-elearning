package nl.hearteye.elearning.ui.components.answerbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .padding(8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .shadow(
                elevation = 4.dp,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp),
                clip = false
            )
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = answer,
            color = if (isSelected) Color.Black else Color.Gray,
            style = typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = isSelected,
            onCheckedChange = onCheckedChange,
            colors = androidx.compose.material3.CheckboxDefaults.colors(
                checkedColor = ForegroundPrimary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnswerBarPreview() {
    Column {
    AnswerBar(
        id = "1",
        answer = "The QRS-axis is horizontally left-centered so this is a really long test item to see what happens when the text is really long",
        isSelected = false,
        onCheckedChange = {}
    )
    AnswerBar(
        id = "2",
        answer = "The P-waves are less visible",
        isSelected = true,
        onCheckedChange = {}
    )
        }
}
