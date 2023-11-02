package com.example.littlelemon.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.example.littlelemon.screen.CartScreen
import com.example.littlelemon.screen.HomeScreen
<<<<<<< HEAD
import com.example.littlelemon.screen.LoginScreen
import com.example.littlelemon.screen.MenuItemDetilsScreen
=======
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
import com.example.littlelemon.screen.ProfileScreen
import com.example.littlelemon.screen.SignUpScreen

@SuppressLint("SuspiciousIndentation")
@ExperimentalComposeUiApi
@ExperimentalGlideComposeApi
@ExperimentalMaterial3Api
@Composable
fun MyNavigation(context: Context) {
    val navController = rememberNavController()
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
<<<<<<< HEAD
        composable(Search.route)
        {
            SearchScreen(navController)
        }
        composable(Cart.route)
        {
            CartScreen(navController)
        }
        composable(
            MenuItemDetails.route + "/{${MenuItemDetails.dishID}}",
            arguments = listOf(navArgument(MenuItemDetails.dishID) { type = NavType.IntType })
        ) {
            val id = requireNotNull(it.arguments?.getInt(MenuItemDetails.dishID))
                MenuItemDetilsScreen(navController,id)
        }
=======
>>>>>>> 56d216785f4db1071ef8d8c11d968190a4c3ecd0
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