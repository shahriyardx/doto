package com.shahriyardx.doto.screens.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shahriyardx.doto.LocalNavController
import com.shahriyardx.doto.Screen

@Composable
fun AppLayout(
    modifier: Modifier = Modifier,
    showActionButton: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            val navController = LocalNavController.current

            if (showActionButton) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.AddTodo.route)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = innerPadding.calculateTopPadding() + 10.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}