package com.example.littlelemon

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation(context: Context)
{
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = destination(context))
    {
        composable(Login.route)
        {
            LoginScreen(navController)
        }
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
        composable(Search.route)
        {
            SearchScreen(navController)
        }
    }
}

private fun destination(context: Context):String{
    val sharedPreferences = context.getSharedPreferences("MY_PRE",Context.MODE_PRIVATE)
    if(sharedPreferences.getBoolean("userRegistered",false))
    {
        return Home.route
    }
    else
    {
        return Login.route
    }
}