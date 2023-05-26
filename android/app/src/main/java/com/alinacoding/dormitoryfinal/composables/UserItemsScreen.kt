package com.alinacoding.dormitoryfinal.composables


import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alinacoding.dormitoryfinal.LocalThemeManager
import com.alinacoding.dormitoryfinal.LoginViewModel
import com.alinacoding.dormitoryfinal.module.Items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun UserItemsScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {
    val selectedIndex = remember { mutableStateOf(2) }

    LaunchedEffect(Unit) {
        viewModel.getUserItems()
    }

    val context = LocalContext.current
    val themeManager = LocalThemeManager.current

    Scaffold(
        topBar = { DormAppBar(themeManager, navigator)},
        bottomBar = { CustomBottomBar(navigator, selectedIndex, context) },
//        drawerContent = {
//            val context = LocalContext.current
//            Sidebar(
//                navigator = navigator,
//                currentRoute = UserItemsScreenDestination.route,
//                context = context
//            )
//        },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Sent Forms", style = MaterialTheme.typography.headlineMedium,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 16.dp))

                    viewModel.userItems.value?.let { items ->
                        LazyColumn {
                            items(items.size) { index ->
                                val item = items[index]
                                ItemCard(item)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ItemCard(item: Items) {
    OutlinedCard(
//        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer),
        //elevation = 4.dp,
        modifier = Modifier.fillMaxWidth().
        size(height = 170.dp, width = 480.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = item.name ?: "",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.descriptionDorm ?: "",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.problem ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
