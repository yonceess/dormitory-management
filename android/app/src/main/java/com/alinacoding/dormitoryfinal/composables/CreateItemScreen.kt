package com.alinacoding.dormitoryfinal.composables

import android.annotation.SuppressLint
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alinacoding.dormitoryfinal.LocalThemeManager
import com.alinacoding.dormitoryfinal.LoginViewModel
import com.alinacoding.dormitoryfinal.composables.destinations.ArticleFeedScreenDestination
import com.alinacoding.dormitoryfinal.composables.destinations.CreateItemScreenDestination

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun CreateItemScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {

    val fullName = remember { mutableStateOf("") }
    val description_dorm = remember { mutableStateOf("") }
    val problem = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val context = LocalContext.current

    val selectedIndex = remember { mutableStateOf(2) }
    val themeManager = LocalThemeManager.current

    Scaffold(
//        drawerContent = {
//            val context = LocalContext.current
//            Sidebar(
//                navigator = navigator,
//                currentRoute = CreateItemScreenDestination.route,
//                context = context
//            )
//        },
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Article Detail") },
//                navigationIcon = {
//                    IconButton(onClick = { navigator.navigate(ArticleFeedScreenDestination.route) }) {
//                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
        topBar = { DormAppBar(themeManager, navigator) },
        bottomBar = {
            CustomBottomBar(navigator, selectedIndex, context) },
        content = {
            Box(modifier =
            Modifier.fillMaxSize()
                .padding(top = 30.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = fullName.value,
                        onValueChange = { fullName.value = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = description_dorm.value,
                        onValueChange = { description_dorm.value = it },
                        label = { Text("Dormitory info") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = problem.value,
                        onValueChange = { problem.value = it },
                        label = { Text("Problem") },
                        singleLine = false,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        label = { Text("Phone") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.createComplaint(
                                fullName = fullName.value,
                                description_dorm = description_dorm.value,
                                problem = problem.value,
                                phone = phone.value,
                                email = email.value
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onSecondary,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Submit Complaint")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}