package com.shahriyardx.doto.screens.todo.todo_list

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun ActionIcon(
    modifier: Modifier,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit,
) {
    IconButton (
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
    }
}