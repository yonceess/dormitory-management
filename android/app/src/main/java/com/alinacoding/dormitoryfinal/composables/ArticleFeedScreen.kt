package com.alinacoding.dormitoryfinal.composables

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.alinacoding.dormitoryfinal.LocalThemeManager
import com.alinacoding.dormitoryfinal.LoginViewModel
import com.alinacoding.dormitoryfinal.composables.destinations.ArticleDetailScreenDestination
import com.alinacoding.dormitoryfinal.composables.destinations.ArticleFeedScreenDestination
import com.alinacoding.dormitoryfinal.module.News
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay


fun convertLocalhostToIp(filePath: String): String {
    return filePath.replace("localhost", "10.0.2.2")
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ArticleFeedScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {

    var searchQuery by remember { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchAndLoadNews()
    }

    val newsArticles by viewModel.newsArticles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedIndex = remember { mutableStateOf(1) }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current
    val themeManager = LocalThemeManager.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).fillMaxSize(),
//        topBar = { DormAppBar(themeManager, navigator) },
        content = {
            Column {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    placeholder = { Text("Hinted search text") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                ){}

                if (isLoading) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    if (searchQuery.isNotEmpty()) {
                        LazyColumn(Modifier.fillMaxHeight()) {
                            itemsIndexed(newsArticles) { index, _ ->
                                if (newsArticles[index].dame.contains(
                                        searchQuery,
                                        true
                                    ) || newsArticles[index].description.contains(searchQuery, true)
                                ) {
                                    NewsItem(newsArticles[index], navigator, viewModel)
                                }
                            }
                        }
                    } else {
                        LazyVerticalStaggeredGrid(
                            columns = StaggeredGridCells.Fixed(2),
                            modifier = Modifier.fillMaxHeight(),
                            contentPadding = PaddingValues(20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalItemSpacing = 26.dp
                        ) {
                            itemsIndexed(newsArticles) { index, _ ->
                                NewsItem(newsArticles[index], navigator, viewModel)
                            }
                        }
                    }
                }
            }
        },
        bottomBar = { CustomBottomBar(navigator, selectedIndex, context) }
    )
}


@Composable
fun NewsItem(
    news: News,
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = viewModel()
) {

    val correctFilePath = convertLocalhostToIp(news.filePath)
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .size(width = 400.dp, height = 200.dp)  // increased height to 200.dp
            .clickable {
                if (!viewModel.isLoading.value) {
                    navigator.navigate(ArticleDetailScreenDestination.route.replace("{newsId}", "${news.id}"))
                }
            },
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                val description = news.description ?: ""
                val shortenedDescription = if (description.length > 60) {
                    description.take(60) + "..."
                } else {
                    description
                }
                Text(text = news.dame, color = Color.Black, fontSize = 16.sp)
                Text(text = shortenedDescription, color = Color.Gray, fontSize = 16.sp)
                Text(text = news.date, color = Color.Gray, fontSize = 12.sp)

                if (news.fileName.endsWith(".docx")) {
                    OutlinedButton(
                        onClick = { viewModel.downloadDocFile(correctFilePath) },
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Download")
                    }
                }
            }

//            if (news.fileName.endsWith(".jpg", ignoreCase = true) || news.fileName.endsWith(".png", ignoreCase = true)) {
//
//                val imageStream by viewModel.imageStream.collectAsState()
//                var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
//
//                DisposableEffect(news.fileName) {
//                    viewModel.downloadImageFile(news.fileName)
//                    onDispose { }
//                }
//
//                DisposableEffect(imageStream) {
//                    imageStream?.let {
//                        val bitmap = BitmapFactory.decodeStream(it)
//                        imageBitmap = bitmap
//                    }
//                    onDispose { }
//                }
//
//                imageBitmap?.let {
//                    val crossfadeState = remember { mutableStateOf(false) }
//                    LaunchedEffect(key1 = it) {
//                        crossfadeState.value = false
//                        delay(100)  // a small delay for loading effect
//                        crossfadeState.value = true
//                    }
//                    AnimatedVisibility(
//                        visible = crossfadeState.value,
//                        enter = fadeIn(),
//                        exit = fadeOut()
//                    ) {
//                        Image(
//                            bitmap = it.asImageBitmap(),
//                            contentDescription = "News Image",
//                            modifier = Modifier
//                                .size(80.dp)
//                                .clip(shape = MaterialTheme.shapes.medium),
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                }
//            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination("article_detail_screen/{newsId}")
@Composable
fun ArticleDetailScreen(
    navBackStackEntry: NavBackStackEntry,
    navigator: DestinationsNavigator
) {
    val viewModel: LoginViewModel = viewModel()
    val newsIdString = navBackStackEntry.arguments?.getString("newsId")
    val newsId = newsIdString?.toIntOrNull()

    val imageStream by viewModel.imageStream.collectAsState()
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    if (newsId != null) {
        DisposableEffect(newsId) {
            viewModel.fetchNewsItem(newsId)
            onDispose { }
        }
    }

    val newsItem by viewModel.newsItem.collectAsState()
    val selectedIndex = remember { mutableStateOf(1) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Article Detail") },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigate(ArticleFeedScreenDestination.route) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            CustomBottomBar(navigator, selectedIndex, context) },
//        drawerContent = {
//            val context = LocalContext.current
//            Sidebar(
//                navigator = navigator,
//                currentRoute = ArticleFeedScreenDestination.route,
//                context = context
//            )
//        },
        content = { innerPadding ->
                Surface(color = Color.White,
                        modifier = Modifier.consumeWindowInsets(innerPadding)
                    )
                {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                    ) {
                        if (newsItem != null) {
                            Text(
                                text = newsItem?.dame ?: "",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Text(
                                text = "Administrative Staff",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            val filename = newsItem?.fileName ?: ""

                            if (filename.endsWith(".jpg", ignoreCase = true) ||
                                filename.endsWith(".png", ignoreCase = true)
                            ) {
                                DisposableEffect(filename) {
                                    viewModel.downloadImageFile(filename)
                                    onDispose { }
                                }

                                DisposableEffect(imageStream) {
                                    imageStream?.let {
                                        val bitmap = BitmapFactory.decodeStream(it)
                                        imageBitmap = bitmap
                                    }
                                    onDispose { }
                                }

                                imageBitmap?.let {
                                    val crossfadeState = remember { mutableStateOf(false) }
                                    LaunchedEffect(key1 = it) {
                                        crossfadeState.value = false
                                        delay(100)  // a small delay for loading effect
                                        crossfadeState.value = true
                                    }
                                    AnimatedVisibility(
                                        visible = crossfadeState.value,
                                        enter = fadeIn(),
                                        exit = fadeOut()
                                    ) {
                                        Image(
                                            bitmap = it.asImageBitmap(),
                                            contentDescription = "News Image",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .clip(shape = MaterialTheme.shapes.medium)
                                                .padding(bottom = 16.dp)
                                        )
                                    }
                                }
                            }

                            else if (filename.endsWith(".docx", ignoreCase = true)) {
                                val correctFilePath = convertLocalhostToIp(newsItem!!.filePath)
                                Button(
                                    onClick = { viewModel.downloadDocFile(correctFilePath) },
                                    modifier = Modifier.padding(top = 16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary,
                                        contentColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Download")
                                }
                            }

                            Text(
                                text = "Published on ${newsItem?.date ?: ""}",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Text(
                                text = newsItem?.description ?: "",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
        }
    )
}
