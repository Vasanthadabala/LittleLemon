package com.example.littlelemon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomBar(navController: NavHostController) {
    val selectedIndex = remember { mutableStateOf(0) }
    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier.fillMaxWidth().
        padding(0.dp)
    ) {
        BottomNavigation(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            backgroundColor = Color.White
        ) {
            BottomNavigationItem(
                selected = (selectedIndex.value == 0),
                onClick = {
                    navController.navigate(Home.route) {
                        popUpTo(Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                    )
                }
            )
            BottomNavigationItem(
                selected = (selectedIndex.value == 1),
                onClick = { },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            )
            BottomNavigationItem(
                selected = (selectedIndex.value == 2),
                onClick = { },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite"
                    )
                }
            )
            BottomNavigationItem(
                selected = (selectedIndex.value == 3),
                onClick = {
                    navController.navigate(Profile.route) {
                        popUpTo(Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile"
                    )
                }
            )
        }
    }
}