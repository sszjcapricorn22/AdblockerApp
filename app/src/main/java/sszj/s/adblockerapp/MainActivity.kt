package sszj.s.adblockerapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import sszj.s.adblockerapp.models.WebItem
import sszj.s.adblockerapp.screens.*
import sszj.s.adblockerapp.viewModels.WebViewModel
import kotlin.random.Random
import androidx.lifecycle.viewmodel.compose.viewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            var showDialog by remember { mutableStateOf(false) }
            var selectedCategory by remember { mutableStateOf("") }

            val viewModel: WebViewModel = viewModel()

            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    NavHost(navController = navController, startDestination = "category") {
                        composable("category") {
                            CategoryScreen { category ->
                                selectedCategory = category
                                navController.navigate("detail/$category")
                            }
                        }

                        composable(
                            route = "detail/{category}",
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) {
                            DetailScreen { url ->
                                val encodedUrl = Uri.encode(url)
                                navController.navigate("webview/$encodedUrl")
                            }
                        }


                        composable(
                            "webview/{url}",
                            arguments = listOf(navArgument("url") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val url = Uri.decode(backStackEntry.arguments?.getString("url") ?: "")
                            WebViewScreen(url = url, navController)
                        }
                    }

                    if (showDialog) {
                        AddWebsiteDialog(
                            onDismiss = { showDialog = false },
                            onAdd = { newItem ->
                                viewModel.pushNewWebsite(newItem)
                                showDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddWebsiteDialog(
    onDismiss: () -> Unit,
    onAdd: (WebItem) -> Unit
) {
    var category by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Website") },
        text = {
            Column {
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = url,
                    onValueChange = { url = it },
                    label = { Text("URL") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (category.isNotBlank() && title.isNotBlank() && url.isNotBlank()) {
                    val webItem = WebItem(
                        id = Random.nextInt(1000, 999999),
                        category = category,
                        title = title,
                        url = url
                    )
                    onAdd(webItem)
                    onDismiss()
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
