package com.example.littlelemon.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R

@Composable
fun BottomBar(navController: NavHostController) {

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentRoute = navController.currentBackStackEntry?.destination?.route ?: ""
    selectedItemIndex = items.indexOfFirst { it.title == currentRoute }

    Card(
        elevation = CardDefaults.cardElevation(5.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(1.dp)
    ) {
        NavigationBar(
            tonalElevation = 5.dp,
            containerColor = Color.White
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navController.navigate(item.title){
                            popUpTo(item.title){
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold ,
                            color = Color(0xff0C2D48))
                    },
                    alwaysShowLabel = true,
                    icon = {
                        Box(
                            modifier = Modifier.size(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painterResource(id = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                }
                                ),
                                contentDescription = item.title
                            )
                        }
                    }
                )
            }
        }
    }
}
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = R.drawable.home_selected,
        unselectedIcon = R.drawable.home
    ),
    BottomNavigationItem(
        title = "Search",
        selectedIcon = R.drawable.search,
        unselectedIcon = R.drawable.search
    ),
//    BottomNavigationItem(
//        title = "Settings",
//        selectedIcon = R.drawable.setting_selected,
//        unselectedIcon = R.drawable.settings
//    ),
    BottomNavigationItem(
        title = "Cart",
        selectedIcon = R.drawable.cart_selected,
        unselectedIcon = R.drawable.cart
    )
)