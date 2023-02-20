package com.alina.dormitorydemo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alina.dormitorydemo.R
import com.alina.dormitorydemo.model.MockData
import com.alina.dormitorydemo.model.News
import com.alina.dormitorydemo.navigation.BottomMenu


val newsList = listOf(
    News("Headline 1", R.drawable.dormroom, "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
    News("Headline 2", R.drawable.dormroom_1, "Nullam ac enim ipsum. Vestibulum vestibulum augue quis maximus placerat."),
    News("Headline 3", R.drawable.dormroom_2, "Pellentesque sit amet orci non massa dapibus iaculis. Fusce euismod sem at sollicitudin dignissim.")
)

@Composable
fun NewsScreen() {
    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val filteredNews = newsList.filter {
        it.title.contains(searchText, true) || it.description.contains(searchText, true)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        ExpandableSearchButton()
        Text(text = "Daily news from the dorm",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp),
            fontSize = 24.sp
            )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredNews) { news ->
                var isExpanded by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { isExpanded = !isExpanded },
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(news.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(id = news.image),
                            contentDescription = null,
                            modifier = Modifier
                                .height(100.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (isExpanded) {
                            Text(news.description)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandableSearchButton() {
    var expanded by remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("") }

    Box(modifier = Modifier
        .background(Color.White, RoundedCornerShape(8.dp))
        .height(56.dp)
        .padding(16.dp)
        .clickable(onClick = { expanded = true })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Search, contentDescription = "Search Icon", tint = Color.Gray,
            modifier = Modifier.padding(top = 12.dp))
            Spacer(modifier = Modifier.width(18.dp))
            Text(text.value, color = Color.Black)
        }
        if (expanded) {
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(26.dp)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = {
                    // Handle search action here
                    expanded = false
                }),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        text.value = ""
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear Icon")
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun NewsPreview(){
    Scaffold(
        bottomBar = { BottomMenu(navController = rememberNavController()) }
    ){NewsScreen()}
}