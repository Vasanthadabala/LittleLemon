package com.example.littlelemon.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.navigation.BottomBar
import com.example.littlelemon.navigation.MenuItemDetails
import com.example.littlelemon.navigation.Profile
import com.example.littlelemon.navigation.Signin

@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SettingScreen(navController : NavHostController)
{
    Scaffold(
        bottomBar = { BottomBar(navController = navController ) }
    ) {
        Column(Modifier.padding(top = 50.dp)) {
            SettingScreenComponent(navController)
        }
    }
}

@Composable
fun SettingScreenComponent(navController: NavHostController){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp)
            .clickable { navController.navigate(Profile.route) }
    ){
        Text(
            text = "ProfileScreen",
            fontSize = 24.sp
        )
    }
    Button(
        onClick = {
                clearSharedPreferences(context)
                editor.putBoolean("isLoggedin",false).apply()
                navController.navigate(Signin.route){
                    popUpTo(navController.graph.id){
                        inclusive = true
                    }
                }
        },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 1.dp,
            pressedElevation = 5.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp, bottom = 20.dp, start = 14.dp, end = 14.dp),
        shape = RoundedCornerShape(24),
        colors = ButtonDefaults.buttonColors(Color.Yellow)
    ) {
        Text(
            text = "Log out",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(2.dp)
        )
    }
}
fun clearSharedPreferences(context: Context)
{
    val sharedPreferences = context.getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}