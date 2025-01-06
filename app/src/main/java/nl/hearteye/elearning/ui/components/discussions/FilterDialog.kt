import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.components.buttons.RegularButton
import nl.hearteye.elearning.ui.theme.BackgroundPrimary
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun FilterDialog(
    onClose: () -> Unit,
    onFilterChange: (category: String?, showMyPostsOnly: Boolean) -> Unit
) {
    var showMyPostsOnly by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }  // Single category selection
    val categoryOptions = listOf("Findings", "Case studies", "Interpretation", "Troubleshooting", "Best practices")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)
                .background(BackgroundPrimary)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filter",
                    style = typography.headlineSmall
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Show your posts only",
                    style = typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = showMyPostsOnly,
                    onCheckedChange = { showMyPostsOnly = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = ForegroundPrimary,
                        checkedTrackColor = ForegroundPrimary.copy(alpha = 0.5f),
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Category",
                style = typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column {
                categoryOptions.forEach { category ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedCategory == category,  // Check if this category is selected
                            onClick = { selectedCategory = category },  // Set selected category
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ForegroundPrimary,
                                unselectedColor = ForegroundPrimary.copy(alpha = 0.5f)
                            )
                        )
                        Text(
                            text = category,
                            style = typography.bodyLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RegularButton(
                onClick = {
                    onFilterChange(selectedCategory, showMyPostsOnly)
                    onClose()
                },
                text = "Apply",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

