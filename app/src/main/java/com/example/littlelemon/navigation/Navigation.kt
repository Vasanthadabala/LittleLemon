package com.example.littlelemon.navigation

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.littlelemon.screen.HomeScreen
import com.example.littlelemon.screen.ProfileScreen
import com.example.littlelemon.screen.SignUpScreen

@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation(context: Context)
{
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = destination(context))
    {
        composable(Signup.route)
        {
            SignUpScreen(navController)
        }
        composable(Home.route)
        {
            HomeScreen(navController)
        }
        composable(Profile.route)
        {
            ProfileScreen(navController)
        }
    }
}

fun destination(context: Context):String{
    val sharedPreferences = context.getSharedPreferences("MY_PRE",Context.MODE_PRIVATE)
    val isLoggedin = sharedPreferences.getBoolean("isLoggedin",false)
    if(isLoggedin)
    {
        return Home.route
    }
    else
    {
        return Signup.route
    }
}