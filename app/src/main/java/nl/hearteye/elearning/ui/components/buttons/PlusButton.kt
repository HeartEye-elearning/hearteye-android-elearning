package nl.hearteye.elearning.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.hearteye.elearning.ui.navigation.NavRoutes
import nl.hearteye.elearning.ui.theme.ForegroundPrimary

@Composable
fun PlusButton(navController: NavController, modifier: Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Surface(
            modifier = Modifier
                .size(40.dp)
                .background(ForegroundPrimary, shape = MaterialTheme.shapes.small)
                .padding(6.dp),
            shape = MaterialTheme.shapes.small,
            color = Color.Transparent
        ) {
            IconButton(
                onClick = {
                    navController.navigate(NavRoutes.DISCUSSIONS_UPLOAD.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Discussion",
                    tint = Color.White
                )
            }
        }
    }
}
