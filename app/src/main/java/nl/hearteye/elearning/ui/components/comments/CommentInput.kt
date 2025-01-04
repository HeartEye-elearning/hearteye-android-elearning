package nl.hearteye.elearning.ui.components.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.ui.theme.ForegroundPrimary
import nl.hearteye.elearning.ui.theme.typography

@Composable
fun CommentInput(
    onAddComment: (String, String?) -> Unit,
    selectedComment: Comment?,
    onClearSelectedComment: () -> Unit
) {
    var commentText = remember { mutableStateOf(TextFieldValue()) }

    Column {
        selectedComment?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Replying to '${it.content ?: ""}'",
                        style = typography.bodyMedium.copy(color = Color.White)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { onClearSelectedComment() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
                    .shadow(
                        elevation = 4.dp,
                        shape = if (selectedComment != null) RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        ) else RoundedCornerShape(8.dp),
                        clip = false
                    )
                    .clip(
                        if (selectedComment != null) RoundedCornerShape(
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        ) else RoundedCornerShape(8.dp)
                    )
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
                        Text(
                            text = "Enter your comment",
                            style = typography.bodyMedium.copy(color = Color.Gray)
                        )
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 12.dp),
                    maxLines = 10,
                    singleLine = false
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                IconButton(
                    onClick = {
                        onAddComment(commentText.value.text, selectedComment?.id)
                        commentText.value = TextFieldValue()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ForegroundPrimary, shape = RoundedCornerShape(8.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowUpward,
                        contentDescription = "Upload",
                        tint = Color.White
                    )
                }
            }
        }
    }
}



