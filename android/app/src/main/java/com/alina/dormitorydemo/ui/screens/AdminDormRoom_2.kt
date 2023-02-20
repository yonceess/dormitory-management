package com.alina.dormitorydemo.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.alina.dormitorydemo.navigation.BottomMenu
import com.alina.dormitorydemo.navigation.Screens
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun AdminDormRoom_2(){
    Card(backgroundColor = Color.White, modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            CompletionRectangle()
            CompletionRectangle()
            CompletionRectangle()
        }
        Text(
            text = "Choose the dormitory floor",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 40.dp, top = 90.dp),
            fontSize = 24.sp
        )

        Row(verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(bottom = 150.dp)){

            Button(
                onClick = {

                },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .padding(start = 200.dp)
                    .height(56.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF4056F4),
                        shape = RoundedCornerShape(8.dp)
                    ),
            ) {
                Text(
                    text = "Go Back",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

        }
    }

        Column(
            modifier = Modifier.fillMaxSize().fillMaxWidth(0.5f)
                .padding(start=24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            FloorRectangle("1")
            Spacer(modifier = Modifier.height(8.dp))
            FloorRectangle("3")
            Spacer(modifier = Modifier.height(8.dp))
            FloorRectangle("5")
            Spacer(modifier = Modifier.height(30.dp))
        }

    Column(
        modifier = Modifier.fillMaxSize().fillMaxWidth(0.5f)
            .padding(end=24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        FloorRectangle("2")
        Spacer(modifier = Modifier.height(8.dp))
        FloorRectangle("4")
        Spacer(modifier = Modifier.height(8.dp))
        FloorRectangle("6")
        Spacer(modifier = Modifier.height(30.dp))
    }

}





@Composable
fun FloorRectangle(text: String) {
    Box(
        modifier = Modifier
            .width(width = 147.dp)
            .height(height = 121.dp)
            .background(color = Color(0xffd1d9ff))
            .border(width = 2.dp,
                color = Color(0xFF4056F4),
                shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(36.dp))
    ){
        Text(text = text, modifier = Modifier.align(Alignment.Center),
            fontSize = 24.sp)
    }
}

@Composable
fun CompletRectangle() {
    Box(
        modifier = Modifier
            .width(width = 100.dp)
            .height(height = 6.dp)
            .background(color = Color(0xff0577c9)))
}

@Preview
@Composable
fun Preview_Room_2(){
    Scaffold(
        bottomBar = { BottomMenu(navController = rememberNavController()) }
    ){AdminDormRoom_2()}
}