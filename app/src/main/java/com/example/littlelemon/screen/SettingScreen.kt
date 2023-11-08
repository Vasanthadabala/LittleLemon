package com.example.littlelemon.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.navigation.BottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun SettingScreen(navController : NavHostController)
{
    Scaffold(
        bottomBar = { BottomBar(navController = navController ) }
    ) {
        Column(Modifier.padding(top = 50.dp)) {
            SettingScreenComponent()
        }
    }
}

@Composable
fun SettingScreenComponent(){
    Text(text = "Settings")
}