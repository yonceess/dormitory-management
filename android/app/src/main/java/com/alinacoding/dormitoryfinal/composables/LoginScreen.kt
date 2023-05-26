package com.alinacoding.dormitoryfinal.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.CardDefaults.outlinedCardColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alinacoding.dormitoryfinal.LoginViewModel
import com.alinacoding.dormitoryfinal.R
import com.alinacoding.dormitoryfinal.composables.destinations.CreateItemScreenDestination
import com.alinacoding.dormitoryfinal.composables.destinations.LoginScreenDestination
import com.alinacoding.dormitoryfinal.composables.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun LoginScreen(navigator: DestinationsNavigator, loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.loginvector),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.46f),
            contentScale = ContentScale.Crop
        )

        // Login form
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 25.dp)
                .height(400.dp),
            colors = elevatedCardColors(containerColor = Color.White)
            //elevation = 8.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Email field
                OutlinedTextField(
                    value = email.text,
                    onValueChange = { email = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Email")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password.text,
                    onValueChange = { password = TextFieldValue(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Password")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                )

                OutlinedButton(
                    onClick = {
                        val emailText = email.text
                        val passwordText = password.text
                        loginViewModel.authenticateUser(emailText, passwordText)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .height(56.dp),
                    colors = outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Login,
                        contentDescription = "Login",
                        tint = Color.White
                    )
                    Text(
                        text = "Log In",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        }
    }

    val toastMessage = loginViewModel.toastMessage.value
    if (!toastMessage.isNullOrEmpty()) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        loginViewModel.clearToastMessage()
    }

    val navigateToEmptyPage = loginViewModel.navigateToEmptyPage.collectAsState()

    if (navigateToEmptyPage.value) {
        loginViewModel.resetNavigation()
        navigator.navigate(ProfileScreenDestination.route)
    }
}