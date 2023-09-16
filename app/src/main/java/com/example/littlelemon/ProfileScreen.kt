package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        InfoScreen(navController = navController)
    }
}

@Composable
fun InfoScreen(navController: NavHostController) {

    val context = LocalContext.current
    val sharedPrefernces = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val savedName = sharedPrefernces.getString("UserName", "")
    val savedMail = sharedPrefernces.getString("Mail", "")
    Column(
        Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp)
                .padding(vertical = 20.dp, horizontal = 25.dp)
                .align(Alignment.CenterHorizontally)
        )
        Card(
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(
                text = "Profile Information",
                textAlign = TextAlign.Start,
                fontSize = 28.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Username:$savedName",
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Email:$savedMail",
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp)
            )
        }
        Button(
            onClick = {
                clearSharedPreferences(context)
                navController.navigate(Login.route)
            },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 5.dp,
                pressedElevation = 10.dp,
                disabledElevation = 0.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 25.dp),
            shape = RoundedCornerShape(32),
            colors = ButtonDefaults.buttonColors(Color.Yellow)
        ) {
            Text(
                text = "Log out", textAlign = TextAlign.Center, fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}
fun clearSharedPreferences(context: Context)
{
    val sharedPrefernces = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor = sharedPrefernces.edit()
    editor.clear()
    editor.apply()
}