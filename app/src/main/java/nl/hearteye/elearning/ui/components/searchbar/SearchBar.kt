package nl.hearteye.elearning.ui.components.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp),
                clip = false
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.padding(start = 8.dp)
            )
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(text = "Search...")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(value = "", onValueChange = {})
}
