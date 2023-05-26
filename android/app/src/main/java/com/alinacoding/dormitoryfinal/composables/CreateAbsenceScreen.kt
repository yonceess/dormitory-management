package com.alinacoding.dormitoryfinal.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alinacoding.dormitoryfinal.LocalThemeManager
import com.alinacoding.dormitoryfinal.LoginViewModel
import com.alinacoding.dormitoryfinal.composables.destinations.CreateAbsenceScreenDestination

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Destination
@Composable
fun CreateAbsenceScreen(navigator: DestinationsNavigator, viewModel: LoginViewModel = viewModel()) {

    val fullName = remember { mutableStateOf("") }
    val reason = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }

    val selectedIndex = remember { mutableStateOf(4) }
    val context = LocalContext.current
    val themeManager = LocalThemeManager.current

    Scaffold(
        topBar = { DormAppBar(themeManager, navigator) },
        bottomBar = {
            CustomBottomBar(navigator, selectedIndex, context) },
        content = {
            Surface(
                modifier = Modifier.fillMaxSize().padding(top = 30.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(fullName, "Full Name")
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(reason, "Reason")
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(date, "Date")
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(phone, "Phone")
                    Spacer(modifier = Modifier.height(8.dp))

                    CustomTextField(address, "Email")
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.createAbsence(
                                fullName = fullName.value,
                                reason = reason.value,
                                date = date.value,
                                phone = phone.value,
                                address = address.value
                            )
                        },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit Absence Statement")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}

@Composable
fun CustomTextField(valueState: MutableState<String>, label: String) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {   valueState.value = it },
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    )
}



