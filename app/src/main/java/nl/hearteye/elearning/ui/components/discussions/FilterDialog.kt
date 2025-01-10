import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import nl.hearteye.elearning.ui.utils.CategoryOption

@Composable
fun FilterDialog(
    selectedCategory: String?,
    showMyPostsOnly: Boolean,
    onClose: () -> Unit,
    onFilterChange: (category: String?, showMyPostsOnly: Boolean) -> Unit
) {
    var localShowMyPostsOnly by remember { mutableStateOf(showMyPostsOnly) }
    var localSelectedCategory by remember { mutableStateOf(selectedCategory) }
    val categoryOptions = CategoryOption.values().toList()

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
                    checked = localShowMyPostsOnly,
                    onCheckedChange = { localShowMyPostsOnly = it },
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
                            selected = localSelectedCategory == category.name,
                            onClick = {
                                localSelectedCategory = if (localSelectedCategory == category.name) null else category.name
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = ForegroundPrimary,
                                unselectedColor = ForegroundPrimary.copy(alpha = 0.5f)
                            )
                        )
                        Text(
                            text = category.displayName,
                            style = typography.bodyLarge,
                            modifier = Modifier.clickable {
                                localSelectedCategory = if (localSelectedCategory == category.name) null else category.name
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            RegularButton(
                onClick = {
                    onFilterChange(localSelectedCategory, localShowMyPostsOnly)
                    onClose()
                },
                text = "Apply",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


