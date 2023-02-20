package com.alina.dormitorydemo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Login
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.alina.dormitorydemo.R
import com.alina.dormitorydemo.navigation.BottomMenu
import com.alina.dormitorydemo.navigation.Screens

@Composable
fun ProfileScreen() {
    // Mock user data
    val user = User(
        "Alina Jakiyeva",
        "01/01/2002",
        "123",
        "https://www.example.com/profile-picture.jpg",
        "23",
        "Mixed",
        "Atyrau"
    )

    Image(
        painter = painterResource(id = R.drawable.figure_profile),
        contentDescription = "Background Image",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        contentScale = ContentScale.Crop
    )

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(top = 25.dp)
        .fillMaxHeight(),
        elevation = 8.dp,
        backgroundColor = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // User picture
            Image(
                painter = painterResource(id = R.drawable.aki),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )

            // User name
            Text(
                fontSize = 24.sp,
                text = user.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // User date of birth
            Text(
                fontSize = 24.sp,
                text = "Date of birth: ${user.dateOfBirth}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // User dormitory room
            Text(
                fontSize = 24.sp,
                text = "Dormitory: ${user.dormitory}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                fontSize = 24.sp,
                text = "Dormitory apartment: ${user.apartment}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                fontSize = 24.sp,
                text = "Dormitory room: ${user.dormitoryRoom}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                fontSize = 24.sp,
                text = "Home town: ${user.homeTown}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 8.dp)
            )


            // Edit button
            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .height(56.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF4056F4),
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "edit",
                    tint = Color.White
                )
                Text(
                    text = "Edit",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp)
                    .height(56.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF4056F4),
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Icon(
                    imageVector = Icons.Filled.FileCopy,
                    contentDescription = "edit",
                    tint = Color.White
                )
                Text(
                    text = "Fill a form",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}



data class User(
    val name: String,
    val dateOfBirth: String,
    val dormitoryRoom: String,
    val profilePictureUrl: String,
    val apartment: String,
    val dormitory: String,
    val homeTown: String
)

@Preview
@Composable
fun ProfilePreview(){
    Scaffold(
        bottomBar = { BottomMenu(navController = rememberNavController()) }
    ){ProfileScreen()}
}