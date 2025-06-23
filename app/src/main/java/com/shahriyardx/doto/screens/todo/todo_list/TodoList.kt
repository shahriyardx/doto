package com.shahriyardx.doto.screens.todo.todo_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shahriyardx.doto.viewmodels.todo.TodoFilter
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TodoList(modifier: Modifier) {
    val viewModel = koinViewModel<TodoViewModel>()
    val tabItems = listOf(
        TabItem(
            "All",
            Icons.AutoMirrored.Filled.List,
            selectedIcon = Icons.AutoMirrored.Filled.List,
            filterType = TodoFilter.ALL
        ), TabItem(
            "Incomplete",
            Icons.Default.Refresh,
            selectedIcon = Icons.Default.Refresh,
            filterType = TodoFilter.INCOMPLETE
        ), TabItem(
            "Complete",
            Icons.Default.Check,
            selectedIcon = Icons.Default.Check,
            filterType = TodoFilter.COMPLETED
        )
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        tabItems.size
    }

    LaunchedEffect(selectedIndex) {
        pagerState.animateScrollToPage(selectedIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedIndex = pagerState.currentPage
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.openDialog()
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(selectedTabIndex = selectedIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        text = { Text(item.title) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        })
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { index ->
                TodoByFilter(tabItems[index].filterType)
            }
        }
    }

    TodoAddDialog(viewModel)

}