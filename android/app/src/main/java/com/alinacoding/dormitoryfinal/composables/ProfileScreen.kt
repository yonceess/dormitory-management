package com.alinacoding.dormitoryfinal.composables

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.DrawerDefaults.scrimColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alinacoding.dormitoryfinal.LocalThemeManager
import com.alinacoding.dormitoryfinal.LoginViewModel
import com.alinacoding.dormitoryfinal.R
import com.alinacoding.dormitoryfinal.composables.destinations.*
import com.alinacoding.dormitoryfinal.module.User
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    loginViewModel: LoginViewModel = viewModel()
) {
    val userProfile = loginViewModel.userProfile.value
    //val selectedIndex = remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        if (userProfile == null) {
            loginViewModel.getUserProfile()
        }
    }
    val selectedIndex = remember { mutableStateOf(1) }
    val context = LocalContext.current
    val themeManager = LocalThemeManager.current


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            DormAppBar(
                themeManager,
                navigator
            )
        },
        bottomBar = {
            CustomBottomBar(navigator, selectedIndex, context)
        },
        content = {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(22.dp))

                SidebarButton(
                    text = "Feed",
                    icon = Icons.Default.Home, // Added icon
                    onClick = { navigator.navigate(PostQRScreenDestination.route) }
                )
                Spacer(modifier = Modifier.height(24.dp))

                SidebarButton(
                    text = "Feed",
                    icon = Icons.Default.Home, // Added icon
                    onClick = { navigator.navigate(ArticleFeedScreenDestination.route) }
                )
                Spacer(modifier = Modifier.height(24.dp))
                SidebarButton(
                    text = "Profile",
                    icon = Icons.Default.Person, // Added icon
                    onClick = { navigator.navigate(ProfileScreenDestination.route) }
                )
                Spacer(modifier = Modifier.height(24.dp))
                SidebarButton(
                    text = "Complaint Forms",
                    icon = Icons.Default.Report,
                    onClick = { navigator.navigate(CreateItemScreenDestination.route) }
                )

                Spacer(modifier = Modifier.height(24.dp))
                SidebarButton(
                    text = "Absence Form",
                    icon = Icons.Default.Mail,
                    onClick = { navigator.navigate(CreateAbsenceScreenDestination.route) }
                )
                Spacer(modifier = Modifier.height(24.dp))
                SidebarButton(
                    text = "Absence Form",
                    icon = Icons.Default.FullscreenExit,
                    onClick = { navigator.navigate(UserItemsScreenDestination.route) }
                )

                Spacer(modifier = Modifier.height(16.dp))

//                items.forEach { item ->
//                    NavigationDrawerItem(
//                        icon = { Icon(item, contentDescription = null) },
//                        label = { Text(item.name) },
//                        selected = item == selectedItem.value,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            selectedItem.value = item
//                        },
//                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                }
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                if (userProfile != null) {
                    OutlinedCard(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,

                            contentColor = Color.Black
                        ),
                        modifier = Modifier.size(width = 390.dp, height = 540.dp).align(Alignment.Center)
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                            .wrapContentHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // User picture
                            Image(
                                painter = painterResource(id = R.drawable.aki),
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(128.dp)
                                    .padding(top = 8.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // User name
                            Text(
                                text = "${userProfile.firstname} ${userProfile.lastname}",
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(8.dp),
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            DisplayInformation(label = "Dormitory", value = userProfile.dormitory)
                            Spacer(modifier = Modifier.height(16.dp))

                            DisplayInformation(label = "Dormitory apartment", value = userProfile.apartment)
                            Spacer(modifier = Modifier.height(16.dp))

                            DisplayInformation(label = "User's email", value = userProfile.email)
                            Spacer(modifier = Modifier.height(16.dp))

                            DisplayInformation(label = "Dormitory room", value = userProfile.room.toString())

                        }
                    }
                } else {
                    CircularProgressIndicator(modifier = Modifier.wrapContentSize(Alignment.Center))
                    // or
                    Text(
                        text = "Failed to load user profile.",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    )
        }
    )
}


////        drawerContent = {
////            val context = LocalContext.current
////            Sidebar(
////                navigator = navigator,
////                currentRoute = ProfileScreenDestination.route,
////                context = context
////            )
//        }



@Composable
fun DisplayInformation(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
        Text(value, style = MaterialTheme.typography.bodyLarge, color = Color.Black)
    }
    Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f), thickness = 0.5.dp)
}

