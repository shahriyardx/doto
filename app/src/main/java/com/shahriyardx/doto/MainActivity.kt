package com.shahriyardx.doto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shahriyardx.doto.ui.theme.DoToTheme
import com.shahriyardx.doto.viewmodels.todo.LocalViewModelComposition
import com.shahriyardx.doto.viewmodels.todo.TodoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<TodoViewModel>()

    // Other ways
    // private val viewModel = getViewModel<TodoViewModel>() // Also Possible
    // private val database by inject<Database>() // Only initialize database once we use database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoToTheme {
                CompositionLocalProvider(LocalViewModelComposition provides viewModel) {
                    Navigation()
//                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(innerPadding)
//                                .padding(horizontal = 16.dp)
//                                .verticalScroll(rememberScrollState())
//                        ) {
//                            TodoForm()
//                            TodoFilterButtons()
//                            TodoList(modifier = Modifier)
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DoToTheme {
        Greeting("Android")
    }
}