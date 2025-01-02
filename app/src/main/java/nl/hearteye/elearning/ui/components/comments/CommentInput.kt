package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CommentInput(onAddComment: (String) -> Unit) {
    var commentText = remember { mutableStateOf(TextFieldValue()) }

    Row {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {
            TextField(
                value = commentText.value,
                onValueChange = { commentText.value = it },
                textStyle = typography.bodyMedium,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                placeholder = {
                    Text(text = "Enter your comment", style = typography.bodyMedium.copy(color = Color.Gray))
                }
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        IconButton(
            onClick = {
                onAddComment(commentText.value.text)
                commentText.value = TextFieldValue()
            },
            modifier = Modifier
                .size(32.dp)
                .background(ForegroundPrimary, shape = RoundedCornerShape(8.dp))
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_upload),
                contentDescription = "Upload",
                tint = Color.White // Icon color
            )
        }
    }
}
