package com.shahriyardx.doto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TodoFilterButtons() {
    val viewModel = LocalViewModelComposition.current
    val state = viewModel.state.collectAsState()

    Row {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TodoFilter.entries.forEach { filterType ->
                Row(
                    modifier = Modifier.clickable {
                        viewModel.onEvent(TodoAction.Filter(filterType))
                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = state.value.filterType == filterType,
                        onClick = { viewModel.onEvent(TodoAction.Filter(filterType)) })

                    Text(filterType.name)
                }
            }
        }
    }
}