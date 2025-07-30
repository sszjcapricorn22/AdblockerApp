package sszj.s.adblockerapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import sszj.s.adblockerapp.viewModels.ItemViewModel

@Composable
fun DetailScreen(onItemClick: (String) -> Unit) {
    val detailViewModel: ItemViewModel = hiltViewModel()
    val websites = detailViewModel.tweets.collectAsState()

    LazyColumn {
        items(websites.value) { item ->
            WebsiteListItem(
                title = item.title,
                category = item.category,
                url = item.url,
                onClick = { onItemClick(item.url) }
            )
        }
    }
}

@Composable
fun WebsiteListItem(
    title: String,
    category: String,
    url: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        border = BorderStroke(1.dp, Color(0xFFCCCCCC))
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = category,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(16.dp, 5.dp, 0.dp, 5.dp)
        )
        Text(
            text = url,
            fontSize = 16.sp,
            color = Color.Blue,
            modifier = Modifier.padding(16.dp, 5.dp, 0.dp, 5.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
