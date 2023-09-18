package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.BottomBar
import com.example.littlelemon.navigation.Login

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

@ExperimentalMaterial3Api
@Composable
fun InfoScreen(navController: NavHostController) {

    val context = LocalContext.current
    val sharedPrefernces = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val savedName = sharedPrefernces.getString("UserName", "Default Name for Login")?:"John"
    val savedMail = sharedPrefernces.getString("Mail", "")?:"John@mail.com"
    val editor = sharedPrefernces.edit()
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
        Text(
            text = "Profile Information",
            textAlign = TextAlign.Start,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp )
        )
        Column {
            Text(
                text = "Username",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(top = 10.dp, start = 14.dp)
            )
            OutlinedTextField(
                value = savedName,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 14.dp),
                shape = RoundedCornerShape(15)
            )
            Text(
                text = "Email",
                textAlign = TextAlign.Start,
                fontSize = 20.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(top = 10.dp, start = 14.dp)
            )
            OutlinedTextField(
                value = savedMail,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                textStyle = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp, horizontal = 14.dp),
                shape = RoundedCornerShape(15),
            )
        }
        Button(
            onClick = {
                clearSharedPreferences(context)
                editor.putBoolean("isLoggedin",false).apply()
                navController.navigate(Login.route){
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
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