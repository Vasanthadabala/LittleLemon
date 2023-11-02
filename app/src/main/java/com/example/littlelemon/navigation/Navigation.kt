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
import com.example.littlelemon.screen.MenuItemDetilsScreen
import com.example.littlelemon.screen.ProfileScreen
import com.example.littlelemon.screen.SearchScreen
import com.example.littlelemon.screen.SignInScreen
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
        composable(Signin.route)
        {
            SignInScreen(navController)
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
    }
}

fun destination(context: Context):String{
    val sharedPreferences = context.getSharedPreferences("MY_PRE",Context.MODE_PRIVATE)
    val isSignedin = sharedPreferences.getBoolean("isSignedin",false)
    val isSignedup = sharedPreferences.getBoolean("isSignedup",false)
    if(isSignedin || isSignedup)
    {
        return Home.route
    }
    else
    {
        return Signin.route
    }
}